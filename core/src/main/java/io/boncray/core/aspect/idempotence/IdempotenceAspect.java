package io.boncray.core.aspect.idempotence;

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
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
@ConditionalOnClass(value = RedisTemplate.class)
public class IdempotenceAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    private static final String CACHE_KEY_PREFIX = "idem";

    /**
     * 带超时的map，过期比较老的锁
     */
    private static final Map<String, ReentrantLock> LOCK_MAP = ExpiringMap.builder()
            .maxSize(300)
            .expiration(10, TimeUnit.SECONDS)
            .variableExpiration()
            .expirationPolicy(ExpirationPolicy.CREATED)
            .build();

    @Around("@annotation(io.boncray.core.aspect.idempotence.Idempotence)")
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
        return group + ":" + MD5.create().digestHex(key, Charset.defaultCharset());
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

        ReentrantLock lock = LOCK_MAP.computeIfAbsent(key, (x) -> new ReentrantLock());
        if (!lock.tryLock(getTimeout(idempotence), idempotence.unit())) {
            throw new IdempotenceTimeOutException("idempotence timeout");
        }

        boolean acquired;
        try {
            // 从redis获得执行权
            while (!(acquired = obtainIdem(key)) && begin < getExpire(idempotence, begin)) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            // 如果没有得到锁，说明超时了
            if (!acquired) {
                throw new IdempotenceTimeOutException("idempotence timeout");
            }
        } finally {
            lock.unlock();
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
        return idempotence.timeout() < 0 ? Long.MAX_VALUE : begin + idempotence.timeout();
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
        ValueOperations<String, Object> forValue = redisTemplate.opsForValue();
        if (BooleanUtil.isTrue(forValue.getOperations().hasKey(key))) {
            forValue.getOperations().delete(key);
        }
    }

}
