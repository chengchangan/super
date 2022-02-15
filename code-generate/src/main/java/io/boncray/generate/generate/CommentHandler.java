package io.boncray.generate.generate;



import io.boncray.generate.domain.Table;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/18 11:19
 */
public interface CommentHandler {

    /**
     * 获取表和列信息
     *
     * @param connection
     * @param database
     * @param tables
     * @return
     */
    Map<String, Table> getTableInfo(Connection connection, String database, List<String> tables);

}
