package com.cca.core.proxy;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.StaticApplicationContext;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/26 19:31
 */
@Configuration
public class CoreStaticApplicationContext extends StaticApplicationContext {


    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AnnotationAutoRetryProxyCreator());

    }
}
