package com.cca.web.web;

import com.cca.core.generator.GeneratorApplication;
import com.cca.web.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/21 19:59
 */
public class Test extends BaseTest {

    @Autowired
    private GeneratorApplication generatorApplication;

    @org.junit.Test
    public void test01() {
        generatorApplication.generate(null);
    }
}