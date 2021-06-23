package com.cca.core.proxy.test;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/23 19:40
 */

public class UserImpl implements User {

    public void save() {
        int a = 1/0;
        System.out.println("save ...");
    }
}
