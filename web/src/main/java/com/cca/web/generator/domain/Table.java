package com.cca.web.generator.domain;

import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/14 10:06
 */
@Data
public class Table {

    /**
     * 表名
     */
    private String tableName;
    /**
     * 备注
     */
    private String comment;

    /**
     * 类名
     */
    private String className;

    /**
     * import 外部类
     */
    private Set<String> domainImports;

    /**
     * 表有哪些列
     */
    private List<Column> columnList;

    /**
     * 所属模块
     */
    private String module;


    public String getColumns() {
        List<String> columns = columnList.stream().map(Column::getColumnName).collect(Collectors.toList());
        return String.join(",", columns);
    }

    public Column getPkColumn() {
        Optional<Column> optional = columnList.stream().filter(Column::isPkColumn).findFirst();
        return optional.orElse(null);
    }

}
