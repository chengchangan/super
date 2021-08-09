package io.boncray.logback.autoConfigurition;

import io.boncray.logback.config.LogBackConfiguration;
import io.boncray.logback.filter.LogbackFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/9 21:13
 */
@Configuration
@ConditionalOnClass(LogbackFilter.class)
@EnableConfigurationProperties({LogBackConfiguration.class})
public class LogBackAutoConfiguration {

}
