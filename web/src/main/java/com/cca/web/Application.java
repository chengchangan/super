package com.cca.web;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/23 14:33
 */
//@EnableWebMvc //todo 测试专用
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class}, scanBasePackages = "com.cca")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

//        BigDecimal multiply = new BigDecimal("0.02").multiply(new BigDecimal("100"));
//        System.out.println(multiply);

    }
}
