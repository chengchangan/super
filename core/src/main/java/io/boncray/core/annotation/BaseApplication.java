package io.boncray.core.annotation;

import io.boncray.core.config.BaseScanConfig;
import io.boncray.core.config.RedisConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/29 15:59
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({
        RedisConfiguration.class,
        BaseScanConfig.class
})
//@EnableConfigurationProperties({XxlExecutorConfig.class})
public @interface BaseApplication {
}
