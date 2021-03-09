package com.cca.web.generator.supperClass.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 查询基类
 *
 * @author cca
 * @version 1.0
 * @date 2021/1/20 15:39
 */
public class Query {

    @ApiModelProperty(value = "查询列", hidden = true)
    private String queryFields;

    public String getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(String queryFields) {
        this.queryFields = queryFields;
    }
}
