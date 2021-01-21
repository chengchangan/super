package com.cca.web;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/21 19:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BaseTest.Config.class)
public class BaseTest {


    @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
            JpaRepositoriesAutoConfiguration.class}, scanBasePackages = "com")
    public static class Config {

    }

}
