package com.cca.core.generator.domain;

import lombok.Data;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/21 16:44
 */
@Data
public class GenConfig {

    /**
     * 包跟路径
     */
    private String basePackage;

    /**
     * 需要生成那些表的代码
     */
    private List<String> generateTableList;

    /**
     * 是否忽略表的第一个字符
     */
    private boolean ignoreTableFirstWord;

    /**
     * 数据源名称
     */
    private String dataSource;

}
