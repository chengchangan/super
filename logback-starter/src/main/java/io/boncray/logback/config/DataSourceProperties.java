package io.boncray.logback.config;

import lombok.Data;

import javax.sql.DataSource;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/8 02:13
 */
@Data
public class DataSourceProperties {

    private Class<? extends DataSource> type;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
