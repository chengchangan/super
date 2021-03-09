package com.cca.web.generator.generate;


import com.cca.web.generator.domain.Configuration;
import com.cca.web.generator.domain.Table;
import com.cca.web.generator.domain.TemplateConfig;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/18 11:19
 */
public interface TemplateParse {

    /**
     * 根据模板 和 参数生成 文件字符串
     *
     * @param config 参数
     * @param templatePath 模板地址
     * @param table 使用表结构
     * @return 文件字符串
     */
    String parseTemplate(Configuration config, String templatePath, Table table);

    /**
     * 生成文件存放的全路径
     *
     * @param basePackage　基础包
     * @param template　模板配置
     * @param table 表
     * @return 文件存放目录
     */
    String getWritePath(String basePackage, TemplateConfig template, Table table);
}
