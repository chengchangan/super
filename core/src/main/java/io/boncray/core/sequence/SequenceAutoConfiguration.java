package io.boncray.core.sequence;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cca
 * @version 1.0
 * @date 2021/3/9 9:42
 */
@Configuration
public class SequenceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "idGenerator")
    public IdGenerator idGenerator() throws Exception {
        return new DefaultIdGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(name = "normalIdGenerator")
    public IdGenerator normalIdGenerator() {
        return new NormalIdGenerator();
    }

}
