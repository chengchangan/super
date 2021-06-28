package com.cca.core.proxy.test;


import com.cca.core.proxy.RetryProxyFactory;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/23 19:44
 */
public class Test {

    private static final RetryProxyFactory PROXY_FACTORY = new RetryProxyFactory(3, 2);


    public static void main(String[] args) {
        // 接口
        User user = new UserImpl();
        // 类
        CgUserImpl cgUser = new CgUserImpl();


        // 获取代理对象
        User instance = PROXY_FACTORY.getProxy(user, User.class);

        // 使用代理对象调用方法
        System.out.println("开始调用");
        instance.save();


    }

}
