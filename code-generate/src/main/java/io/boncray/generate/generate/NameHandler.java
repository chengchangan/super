package io.boncray.generate.generate;


import io.boncray.generate.domain.Configuration;
import io.boncray.generate.domain.Table;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/18 11:19
 */
public interface NameHandler {


    /**
     * 转换表名->类名
     * 列名->字段名
     *
     * @param configuration 配置信息
     * @param table         表信息
     */
    void processTableToClass(Configuration configuration, Table table);

}
