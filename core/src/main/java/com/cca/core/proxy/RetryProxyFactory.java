package com.cca.core.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/23 19:56
 */
public class RetryProxyFactory<T> {

    private static Logger logger = LoggerFactory.getLogger(RetryProxyFactory.class);

    /**
     * 目标对象
     */
    private final T target;
    /**
     * 重试间隔的时间
     */
    int intervalSecond;

    /**
     * 重试的次数
     */
    int times;


    public RetryProxyFactory(T target) {
        this(target, 3, 3);
    }

    public RetryProxyFactory(T target, int intervalSecond, int times) {
        this.target = target;
        this.intervalSecond = intervalSecond;
        this.times = times;
    }


    public T getInstance() {
        Class<?>[] interfaces = target.getClass().getInterfaces();
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

        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws InterruptedException {
            // 执行目标对象的方法
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
            throw new RuntimeException("服务调用失败，方法：" + method.getName() + "，参数：" + Arrays.toString(args) + "，重试次数：" + times + "，cause:" + exception);

        }

    }


    class JdkProxy {

        private final Object target;// 维护一个目标对象

        public JdkProxy(Object target) {
            this.target = target;
        }

        // 为目标对象生成代理对象
        public Object getProxyInstance() {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                    new InvocationHandler() {
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
                            throw new RuntimeException("服务调用失败，方法：" + method.getName() + "，参数：" + Arrays.toString(args) + "，重试次数：" + times + "，cause:" + exception);
                        }
                    });
        }

    }

}
