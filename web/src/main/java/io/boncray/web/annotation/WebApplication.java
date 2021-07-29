package io.boncray.web.annotation;

import io.boncray.core.annotation.BaseApplication;
import io.boncray.web.config.SwaggerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/29 15:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({
//        CorsFilterConfig.class,
        SwaggerConfig.class,
//        LocalDateTimeSerializerConfig.class,
//        SpecialRooterFeignConfig.class,
//        EurekaInstanceUltimateConfig.class
})
@BaseApplication
public @interface WebApplication {
}
