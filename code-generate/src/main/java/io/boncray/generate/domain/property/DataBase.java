package io.boncray.generate.domain.property;

import lombok.Data;

@Data
public class DataBase {
    private String driverClass;
    private String url;
    private String user;
    private String password;
}
