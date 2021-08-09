package io.boncray.example;

import io.boncray.core.annotation.BaseApplication;
import io.boncray.example.log.LogBackConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/28 11:11
 */
@EnableConfigurationProperties({LogBackConfiguration.class})
@BaseApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class}, scanBasePackages = "io.boncray")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}
