package io.boncray.core.aspect.retry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/28 9:28
 */
@Aspect
@Component
public class RetryAspect {

    private static final Logger logger = LoggerFactory.getLogger(RetryAspect.class);

    @Around("@annotation(io.boncray.core.aspect.retry.Retry)")
    public Object aroundExec(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Retry retry = method.getAnnotation(Retry.class);
        int times = retry.times();
        int intervalSecond = retry.intervalSecond();
        Class<? extends Throwable>[] classes = retry.retryFor();

        // 执行目标对象的方法
        Object[] args = joinPoint.getArgs();
        Throwable exception = null;
        for (int i = 0; i < times; i++) {
            try {
                return joinPoint.proceed(args);
            } catch (Throwable e) {
                // 如果不是指定异常，则抛出，不进行重试
                if (classes.length > 0) {
                    boolean needRetry = Arrays.stream(classes).anyMatch(clazz -> e.getClass().equals(clazz) || clazz.isAssignableFrom(e.getClass()));
                    if (!needRetry) {
                        throw e;
                    }
                }
                TimeUnit.SECONDS.sleep(intervalSecond);
                logger.error("方法调用失败:{}，参数:{}，重试次数:{}", method.getName(), args, i + 1);
                if (e instanceof InvocationTargetException) {
                    exception = ((InvocationTargetException) e).getTargetException();
                } else {
                    exception = e;
                }
            }
        }
        if (exception == null) {
            exception = new RuntimeException("未知异常");
        }
        logger.error("服务调用失败，方法:{}，参数:{}，重试次数:{}，cause:{}", method.getName(), Arrays.toString(joinPoint.getArgs()), times, exception.getMessage());
        throw exception;
    }


}
