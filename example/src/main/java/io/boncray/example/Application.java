package io.boncray.example;

import io.boncray.web.annotation.WebApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/28 11:11
 */

@WebApplication
@EnableDiscoveryClient
@SpringBootApplication(
//        exclude = {DataSourceAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class},
        scanBasePackages = "io.boncray")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}
