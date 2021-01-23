package com.cca;

import org.springframework.boot.SpringApplication;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/23 14:33
 */
//@EnableWebMvc todo 测试专用
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
//        JpaRepositoriesAutoConfiguration.class}, scanBasePackages = "com.cca")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
