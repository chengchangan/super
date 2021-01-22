package com.cca.core.generator.domain;

import com.cca.core.generator.domain.property.DataBase;
import lombok.Data;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/14 10:06
 */
@Data
public class Configuration {


    /**
     * 包的根路径
     */
    private String basePackage;

    /**
     * 数据源
     */
    private DataBase database;

    /**
     * 模板信息
     */
    private List<TemplateConfig> templateList;

    /**
     * 需要生成的表
     */
    private List<String> generateTableList;
    /**
     * 忽略的字段
     */
    private List<String> ignoreFields;

    /**
     * 忽略表第一个字符
     */
    private boolean ignoreTableFirstWord;

    /**
     * 是否忽略生成service代码
     */
    private boolean ignoreServiceCode;


}
