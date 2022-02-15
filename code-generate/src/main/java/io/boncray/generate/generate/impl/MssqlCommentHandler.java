package io.boncray.generate.generate.impl;


import cn.hutool.core.collection.CollectionUtil;
import io.boncray.generate.domain.Column;
import io.boncray.generate.domain.Table;
import io.boncray.generate.generate.CommentHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 获取Mysql表信息
 *
 * @author cca
 * @version 1.0
 * @date 2021/1/18 11:19
 */
public class MssqlCommentHandler implements CommentHandler {

    @Override
    public Map<String, Table> getTableInfo(Connection connection, String database, List<String> tables) {
        StringBuilder builder = new StringBuilder("select TABLE_NAME,TABLE_COMMENT from information_schema.tables");
        builder.append(" WHERE TABLE_SCHEMA = '").append(database).append("'");
        if (CollectionUtil.isNotEmpty(tables)) {
            tables = tables.stream().map((table -> "'" + table + "'")).collect(Collectors.toList());

            builder.append(" AND TABLE_NAME IN (").append(String.join(",", tables)).append(")");
        }
        ResultSet rs = runSql(connection, builder.toString());
        return buildTableMap(connection, database, rs);
    }

    private Map<String, Table> buildTableMap(Connection connection, String database, ResultSet rs) {
        Map<String, Table> map = new HashMap<>(100);
        try {
            if (rs != null) {
                while (rs.next()) {
                    Table table = new Table();
                    table.setTableName(rs.getString("TABLE_NAME"));
                    table.setComment(rs.getString("TABLE_COMMENT"));
                    table.setColumnList(getColumnInfo(connection, database, table));
                    table.setDomainImports(new HashSet<>());
                    map.put(table.getTableName(), table);
                }
                return map;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    private List<Column> getColumnInfo(Connection connection, String database, Table table) {
        ResultSet rs = runSql(connection, "select DISTINCT column_name,data_type,column_key,column_comment,ORDINAL_POSITION from information_schema.columns where TABLE_SCHEMA='" + database + "' AND TABLE_NAME='" + table.getTableName() + "' order By ORDINAL_POSITION");
        return buildColumn(rs);
    }

    private List<Column> buildColumn(ResultSet rs) {
        Map<String, Column> columnMap = new LinkedHashMap<>();
        try {
            if (rs != null) {
                while (rs.next()) {
                    Column column = new Column();
                    column.setColumnName(rs.getString("column_name").trim());
                    column.setColumnType(rs.getString("data_type").trim());
                    column.setComment(rs.getString("column_comment").trim());
                    column.setPkColumn("PRI".equals(rs.getString("column_key")));
                    columnMap.put(column.getColumnName(), column);
                }
                return new ArrayList<>(columnMap.values());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private ResultSet runSql(Connection connection, String sql) {
        try {
            Statement st = connection.createStatement();
            return st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
