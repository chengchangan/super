package com.cca.web.config;

import cn.hutool.core.collection.CollectionUtil;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/23 15:51
 */
public class SwaggerUtil {

    private SwaggerUtil() {
    }

    public static Docket docket(String group, String basePackage) {
        return docket(group, null, basePackage, null);
    }

    public static Docket docket(String group, ApiInfo apiInfo, String basePackage, List<Parameter> parameters) {
        if (apiInfo == null) {
            apiInfo = createApiInfo("接口平台", "赢在速度 赢在执行力", "1.0.0");
        }
        Docket build = new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo)
                .groupName(group)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
        if (CollectionUtil.isNotEmpty(parameters)) {
            build.globalOperationParameters(parameters);
        }
        return build;
    }

    public static Parameter createHeadParam(String paramName, String description, String defaultValue) {
        return new ParameterBuilder().name(paramName)
                .description(description)
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue(defaultValue)
                .required(false)
                .build();
    }

    public static ApiInfo createApiInfo(String title, String description, String version) {
        return createApiInfo(title, description, version, null);
    }

    public static ApiInfo createApiInfo(String title, String description, String version, Contact contact) {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version);

        if (contact != null) {
            apiInfoBuilder.contact(contact);
        }
        return apiInfoBuilder.build();
    }


}
