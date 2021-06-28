package com.cca.core.util;

import com.cca.core.proxy.RetryProxyFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/28 15:22
 */
public class ProxyUtil {

    private static final ConcurrentHashMap<String, Object> RETRY_PROXY = new ConcurrentHashMap<>();

    private static final RetryProxyFactory PROXY_FACTORY = new RetryProxyFactory(2, 2);


    public static <T> T getRetryProxy(Object target, Class<T> targetClazz) {
        return getRetryProxy(target, targetClazz, null, null);
    }

    public static <T> T getRetryProxy(Object target, Class<T> targetClazz, Integer times, Integer intervalSecond) {
        String targetClazzName = targetClazz.getName();
        if (RETRY_PROXY.containsKey(targetClazzName)) {
            return (T) RETRY_PROXY.get(targetClazzName);
        }
        T proxy;
        if (times == null || intervalSecond == null) {
            proxy = PROXY_FACTORY.createProxy(target, targetClazz);
        } else {
            proxy = PROXY_FACTORY.createProxy(target, targetClazz, times, intervalSecond);
        }
        RETRY_PROXY.put(targetClazzName, proxy);
        return proxy;
    }

}
