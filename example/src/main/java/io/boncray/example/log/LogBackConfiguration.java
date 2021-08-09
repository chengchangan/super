package io.boncray.example.log;

import io.boncray.logback.config.DataSourceProperties;
import io.boncray.logback.config.TransferChannel;
import io.boncray.logback.config.TransferStrategy;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data

@ConfigurationProperties(prefix = "log.manager")
public class LogBackConfiguration {

    /**
     * 是否需要收集
     */
    private boolean collectEnabled;

    /**
     * 传输的方式, todo 先默认写死，后续读取配置
     */
    private TransferChannel transferChannel = TransferChannel.MYSQL;

    /**
     * mysql数据源
     */
    private DataSourceProperties transferDataSource;


    /**
     * 传输策略
     */
    private TransferStrategy transferStrategy;


}
