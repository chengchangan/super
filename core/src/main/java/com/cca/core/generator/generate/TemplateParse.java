package com.cca.core.generator.generate;


import com.cca.core.generator.domain.Configuration;
import com.cca.core.generator.domain.Table;
import com.cca.core.generator.domain.TemplateConfig;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/18 11:19
 */
public interface TemplateParse {

    String parseTemplate(Configuration config, String template1, Table table);

    String getWritePath(String basePackage, TemplateConfig template1, Table table);
}
