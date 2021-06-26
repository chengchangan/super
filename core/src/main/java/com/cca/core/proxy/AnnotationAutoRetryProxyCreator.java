package com.cca.core.proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/24 11:13
 */
public class AnnotationAutoRetryProxyCreator implements BeanPostProcessor {

    private static final RetryProxyFactory PROXY_FACTORY = new RetryProxyFactory();


    @Override
    public Object postProcessAfterInitialization(@Nullable Object bean, @Nullable String beanName) throws BeansException {

        return PROXY_FACTORY.getProxy(bean, bean.getClass());
    }
}
