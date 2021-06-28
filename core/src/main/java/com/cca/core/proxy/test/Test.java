package com.cca.core.proxy.test;


import com.cca.core.proxy.RetryProxyFactory;
import com.cca.core.util.ProxyUtil;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/23 19:44
 */
public class Test {



    public static void main(String[] args) {
        // 接口
        User user = new UserImpl();
        // 类
        CgUserImpl cgUser = new CgUserImpl();


        // 获取代理对象
        User instance = ProxyUtil.getRetryProxy(user, User.class,5,2);

        // 使用代理对象调用方法
        System.out.println("开始调用");
        instance.save();


    }

}
