package io.boncray.core.util.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/23 19:56
 */
public class RetryProxyFactory {

    private static final Logger logger = LoggerFactory.getLogger(RetryProxyFactory.class);

    /**
     * 重试间隔的时间
     */
    private final int intervalSecond;

    /**
     * 重试的次数
     */
    private final int times;


    public RetryProxyFactory() {
        this(2, 2);
    }

    public RetryProxyFactory(int intervalSecond, int times) {
        this.intervalSecond = intervalSecond * 1000;
        this.times = times;
    }


    /**
     * 默认间隔时间和秒数
     */
    public <T> T createProxy(Object target, Class<T> targetClazz) {
        return this.createProxy(target, targetClazz, this.times, this.intervalSecond);
    }


    public <T> T createProxy(Object target, Class<T> targetClazz, int times, int intervalSecond) {
        Class<?>[] interfaces = targetClazz.getInterfaces();
        if (interfaces.length == 0) {
            // cglib
            return (T) new CglibProxy(target, times, intervalSecond).getProxyInstance();
        } else {
            // jdk
            return (T) new JdkProxy(target, times, intervalSecond).getProxyInstance();
        }

    }


    static class CglibProxy implements MethodInterceptor {

        private final Object target;
        int intervalSecond;
        int times;

        public CglibProxy(Object target, int times, int intervalSecond) {
            this.intervalSecond = intervalSecond;
            this.times = times;
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
            logger.error("服务调用失败，方法:{}，参数:{}，重试次数:{}，cause:{}", method.getName(), Arrays.toString(args), times, exception.getMessage());
            throw exception;
        }

    }


    static class JdkProxy {
        private final Object target;
        int intervalSecond;
        int times;

        public JdkProxy(Object target, int times, int intervalSecond) {
            this.target = target;
            this.intervalSecond = intervalSecond;
            this.times = times;
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
                        logger.error("服务调用失败，方法:{}，参数:{}，重试次数:{}，cause:{}", method.getName(), Arrays.toString(args), times, exception.getMessage());
                        throw exception;
                    });
        }

    }

}
