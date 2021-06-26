package com.cca.core.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/23 19:56
 */
public class RetryProxyFactory {

    private static Logger logger = LoggerFactory.getLogger(RetryProxyFactory.class);

    /**
     * 重试间隔的时间
     */
    int intervalSecond;

    /**
     * 重试的次数
     */
    int times;


    public RetryProxyFactory() {
        this(3, 3);
    }

    public RetryProxyFactory(int intervalSecond, int times) {
        this.intervalSecond = intervalSecond * 1000;
        this.times = times;
    }


    public <T> T getProxy(Object target, Class<T> targetClazz) {
        Class<?>[] interfaces = targetClazz.getInterfaces();
        if (interfaces.length == 0) {
            // cglib
            return (T) new CglibProxy(target).getProxyInstance();
        } else {
            // jdk
            return (T) new JdkProxy(target).getProxyInstance();
        }

    }


    class CglibProxy implements MethodInterceptor {

        private final Object target;

        public CglibProxy(Object target) {
            this.target = target;
        }

        //为目标对象生成代理对象
        public Object getProxyInstance() {
            //工具类
            Enhancer en = new Enhancer();
            //设置父类
            en.setSuperclass(target.getClass());
            //设置回调函数
            en.setCallback(this);
            //创建子类对象代理
            return en.create();
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            // 执行目标对象的方法
            Throwable exception = null;
            for (int i = 0; i < times; i++) {
                try {
                    return method.invoke(target, args);
                } catch (Throwable e) {
                    Thread.sleep(intervalSecond);
                    logger.error("方法调用失败：{}，参数：{}，重试次数：{}", method.getName(), args, i + 1);
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
            logger.error("服务调用失败，方法：{}，参数：{}，重试次数：{}，cause：{}", method.getName(), Arrays.toString(args), times, exception.getMessage());
            throw exception;
        }

    }


    class JdkProxy {

        private final Object target;

        public JdkProxy(Object target) {
            this.target = target;
        }

        // 为目标对象生成代理对象
        public Object getProxyInstance() {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        Throwable exception = null;
                        for (int i = 0; i < times; i++) {
                            try {
                                return method.invoke(target, args);
                            } catch (Exception e) {
                                Thread.sleep(intervalSecond);
                                logger.error("方法调用失败：{}，参数：{}，重试次数：{}", method.getName(), args, i + 1);
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
                        logger.error("服务调用失败，方法：{}，参数：{}，重试次数：{}，cause：{}", method.getName(), Arrays.toString(args), times, exception.getMessage());
                        throw exception;
                    });
        }

    }

}
