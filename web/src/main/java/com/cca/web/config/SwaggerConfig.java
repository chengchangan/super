package com.cca.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/23 14:24
 */
@EnableSwagger2
@Configuration
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class SwaggerConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket createGeneratorRestApi() {
        Parameter param1 = SwaggerUtil.createHeadParam("token", "令牌", "");
        ApiInfo apiInfo = SwaggerUtil.createApiInfo("标题", "代码生成器", "1.0.0");
        return SwaggerUtil.docket("代码生成器", apiInfo, "com.cca.web.generator", Collections.singletonList(param1));
    }

}
