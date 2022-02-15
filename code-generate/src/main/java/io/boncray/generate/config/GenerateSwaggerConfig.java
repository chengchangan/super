package io.boncray.generate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * @author changan
 * @version 1.0
 * @date 2022/2/15 11:19
 */
@Configuration
public class GenerateSwaggerConfig {

    @Bean
    public Docket createGeneratorRestApi() {
        Parameter param1 = SwaggerUtil.createHeadParam("token", "令牌", "");
        ApiInfo apiInfo = SwaggerUtil.createApiInfo("标题", "代码生成器", "1.0.0");
        return SwaggerUtil.docket("代码生成器", apiInfo, "io.boncray.generate", Collections.singletonList(param1));
    }
}
