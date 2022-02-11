package io.boncray.cmdb.database.autoConfigure;

import io.boncray.cmdb.database.intercepter.PageInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Slf4j
@Configuration
public class CustomPlusAutoConfiguration {

    @Order(20)
    @Bean
    PageInterceptor pageInterceptor() {
        return new PageInterceptor();
    }

}
