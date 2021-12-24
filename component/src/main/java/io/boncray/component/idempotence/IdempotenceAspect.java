package io.boncray.component.idempotence;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/19 15:27
 */
@Slf4j
@Aspect
@Component
public class IdempotenceAspect {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedissonClient redissonClient;

    public IdempotenceAspect(RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient) {
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
    }


    private static final String CACHE_KEY_PREFIX = "idem";

    /**
     * 本地锁管理器
     */
    private final LocalLockManager LOCK_MANAGER = new LocalLockManager();

    @Around("@annotation(io.boncray.component.idempotence.Idempotence)")
    public Object aroundExec(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法名、参数列表
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();
        Idempotence idempotence = method.getAnnotation(Idempotence.class);

        // 根据信息生成签名
        String requestSign = signRequest(method, args, idempotence);
        // 校验幂等性，不过则阻塞
        checkRequestIdem(requestSign, idempotence);
        Object proceed;
        try {
            proceed = joinPoint.proceed();
        } finally {
            // 释放
            release(requestSign);
        }
        return proceed;
    }


    /**
     * 生成请求幂等性唯一码
     */
    private String signRequest(Method method, Object[] args, Idempotence idempotence) {
        ExpressionParser parser = new SpelExpressionParser();
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

        String key;
        String keySpel = idempotence.key();
        String group = idempotence.group();

        String[] params = discoverer.getParameterNames(method);
        // 如果有参数，则使用参数生成
        if (ArrayUtil.isNotEmpty(params) && ArrayUtil.isNotEmpty(args) && keySpel.contains("#")) {
            EvaluationContext context = new StandardEvaluationContext();
            for (int len = 0; len < params.length; len++) {
                context.setVariable(params[len], args[len]);
            }
            Expression keyExpression = parser.parseExpression(keySpel);
            key = keyExpression.getValue(context, String.class);
        } else {
            if (StrUtil.isNotBlank(keySpel)) {
                key = keySpel;
            } else {
                // 否则使用方法名作为幂等
                key = method.toGenericString();
            }
        }
        String sign = group + ":" + MD5.create().digestHex(key, Charset.defaultCharset());
        log.debug("签名明文，group：{}，key：{}，签名 sign：{}", group, key, sign);
        return sign;
    }

    /**
     * 根据签名信息，校验幂等性
     * <p>
     * 校验改签名是否有人占用，本地是否被锁住，redis 中是否存在
     *
     * @param sign        请求签名信息
     * @param idempotence 注解参数
     */
    private void checkRequestIdem(String sign, Idempotence idempotence) throws InterruptedException {
        String key = buildKey(sign);
        long begin = System.currentTimeMillis();

        // 本地获取锁
        ReentrantLock lock = LOCK_MANAGER.get(key);
        if (!lock.tryLock(getTimeout(idempotence), idempotence.unit())) {
            throw new IdempotenceTimeOutException("idempotence timeout");
        }
        log.debug("获取本地锁成功");

        boolean acquired;
        long expireTime = getExpire(idempotence, begin);
        try {
            // 从redis获得执行权（redis获取锁）
            while (!(acquired = obtainIdem(key)) && System.currentTimeMillis() < expireTime) {
                log.debug("获取redis锁失败，等待下一次重试");
                TimeUnit.MILLISECONDS.sleep(100);
            }
            // 如果没有得到锁，说明超时了
            if (!acquired) {
                throw new IdempotenceTimeOutException("idempotence timeout");
            }
            log.debug("获取redis锁成功");
        } catch (Throwable e) {
            // redis 获取异常释放本地锁
            lock.unlock();
            throw e;
        }
    }

    /**
     * 超时时间
     */
    private long getTimeout(Idempotence idempotence) {
        return idempotence.timeout() > 0 ? idempotence.timeout() : Long.MAX_VALUE;
    }

    /**
     * 到期时间
     */
    private long getExpire(Idempotence idempotence, long begin) {
        return idempotence.timeout() < 0 ? Long.MAX_VALUE : begin + idempotence.unit().toMillis(idempotence.timeout());
    }


    /**
     * 签名信息
     */
    private String buildKey(String sign) {
        return CACHE_KEY_PREFIX + "_" + sign;
    }

    /**
     * 从redis 获得执行权
     *
     * @param key 请求签名
     * @return 是否获取执行权限
     */
    private boolean obtainIdem(String key) {
        if (redissonClient != null) {
            return redissonClient.getLock(key).tryLock();
        } else {
            ValueOperations<String, Object> forValue = redisTemplate.opsForValue();
            // 跟 redisson 保持一直，都是默认30秒
            return BooleanUtil.isTrue(forValue.setIfAbsent(key, Thread.currentThread().getId(), 30L, TimeUnit.SECONDS));
        }
    }


    /**
     * 释放幂等相关信息
     *
     * @param sign 请求签名
     */
    private void release(String sign) {
        String key = buildKey(sign);
        // 释放本地锁
        ReentrantLock lock = LOCK_MANAGER.get(key);
        if (lock != null && lock.isLocked()) {
            log.debug("释放本地锁，key：{}", key);
            lock.unlock();
        }
        // 释放redis锁
        if (redissonClient != null) {
            RLock rLock = redissonClient.getLock(key);
            if (rLock.isLocked()) {
                log.debug("释放redis锁，key：{}", key);
                rLock.unlock();
            }
        } else {
            ValueOperations<String, Object> forValue = redisTemplate.opsForValue();
            if (BooleanUtil.isTrue(forValue.getOperations().hasKey(key))) {
                log.debug("释放redis锁，key：{}", key);
                forValue.getOperations().delete(key);
            }
        }
    }

    /**
     * 本地锁过期管理
     */
    private static class LocalLockManager {

        private final Object PRESENT = new Object();

        private final Map<String, ReentrantLock> LOCK_MAP = new ConcurrentHashMap<>();

        private final ExpiringMap<String, Object> EXPIRING_MAP = ExpiringMap.builder()
                .maxSize(300)
                .expiration(10, TimeUnit.SECONDS)
                .variableExpiration()
                .expirationPolicy(ExpirationPolicy.ACCESSED)
                .build();

        public LocalLockManager() {
            EXPIRING_MAP.addExpirationListener((key, lock) -> {
                // 如果过期了，并且这个锁还在被使用，则将这个key放回去
                if (LOCK_MAP.containsKey(key) && LOCK_MAP.get(key).isLocked()) {
                    log.debug("锁：{}过期，且在使用中，重新放入", key);
                    EXPIRING_MAP.put(key, PRESENT);
                } else {
                    log.debug("锁已过期：{}", key);
                    LOCK_MAP.remove(key);
                }
            });
        }

        public ReentrantLock get(String key) {
            if (EXPIRING_MAP.containsKey(key)) {
                EXPIRING_MAP.getExpectedExpiration(key);
            } else {
                EXPIRING_MAP.put(key, PRESENT);
            }
            return LOCK_MAP.computeIfAbsent(key, (x) -> new ReentrantLock());
        }

    }

}
