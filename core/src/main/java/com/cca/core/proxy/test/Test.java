package com.cca.core.proxy.test;


import com.cca.core.proxy.RetryProxyFactory;

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

        // 创建代理工厂
        RetryProxyFactory<CgUserImpl> proxyFactory = new RetryProxyFactory<CgUserImpl>(cgUser,0,5);

        // 获取代理对象
        CgUserImpl instance = proxyFactory.getInstance();

        // 使用代理对象调用方法
        instance.save();

    }

}
