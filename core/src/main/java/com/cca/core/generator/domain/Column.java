package com.cca.core.generator.domain;

import lombok.Data;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/14 10:07
 */
@Data
public class Column {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 列的数据库类型
     */
    private String columnType;

    /**
     * 主键列
     */
    private boolean pkColumn;
    /**
     * 备注
     */
    private String comment;


    /**
     * java 属性名
     */
    private String fieldName;

    /**
     * java 类型
     */
    private String javaType;

    /**
     * java 忽略
     */
    private boolean javaIgnore;

    /**
     * setter方法名
     */
    private String setterName;

    /**
     * 　getter方法名
     */
    private String getterName;


}
