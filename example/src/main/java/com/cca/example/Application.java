package com.cca.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/28 11:11
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class}, scanBasePackages = "com.cca")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}
