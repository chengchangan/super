package com.cca.core.generator.generate.impl;


import com.cca.core.generator.TypeConvert;
import com.cca.core.generator.domain.Column;
import com.cca.core.generator.domain.Configuration;
import com.cca.core.generator.domain.Table;
import com.cca.core.generator.generate.NameHandler;
import com.cca.core.util.StringUtil;
import io.micrometer.core.instrument.util.StringUtils;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/18 11:19
 */
public class NameHandlerImpl implements NameHandler {

    @Override
    public void processTableToClass(Configuration configuration, Table table) {
        table.setClassName(parseBeanName(configuration.isIgnoreTableFirstWord(), table.getTableName()));

        table.getColumnList().forEach(column -> {
            processColumnToField(column);
            column.setJavaType(TypeConvert.getType(column.getColumnType()));
            if (configuration.getIgnoreFields().contains(column.getColumnName())) {
                column.setJavaIgnore(true);
            } else {
                String clazz = TypeConvert.getTypeClass(column.getJavaType());
                if (StringUtils.isNotEmpty(clazz)) {
                    table.getDomainImports().add(clazz);
                }
            }
        });
    }

    public void processColumnToField(Column column) {
        String name = parseFieldName(column.getColumnName());
        column.setFieldName(StringUtil.lowCaseFirst(name));
        column.setGetterName("get" + name);
        column.setSetterName("set" + name);
    }

    protected String parseBeanName(boolean ignoreTableFirstWord, String columnName) {
        List<String> words = StringUtil.words(columnName);

        String name = "";
        for (int i = 0; i < words.size(); i++) {
            if (ignoreTableFirstWord && i != 0) {
                name += StringUtil.upCaseFirst(words.get(i));
            }
        }
        return name;
    }

    protected String parseFieldName(String columnName) {
        List<String> words = StringUtil.words(columnName);
        String name = "";
        for (String word : words) {
            name += StringUtil.upCaseFirst(word.toLowerCase());
        }
        return name;
    }


}
