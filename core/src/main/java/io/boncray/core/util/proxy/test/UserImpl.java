package io.boncray.core.util.proxy.test;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/23 19:40
 */

public class UserImpl implements User {

    @Override
    public void save() {
        int a = 1 / 0;
        System.out.println("save ...");
    }
}
