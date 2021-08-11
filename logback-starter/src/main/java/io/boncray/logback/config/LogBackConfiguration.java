package io.boncray.logback.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/9 21:13
 */
@Data
@ConfigurationProperties(prefix = "log.manager")
public class LogBackConfiguration {

    /**
     * 是否需要收集
     */
    private boolean collectEnabled;

    /**
     * 传输的方式
     */
    private TransferChannel transferChannel = TransferChannel.NONE;

    /**
     * mysql数据源
     */
    @NestedConfigurationProperty
    private DataSourceProperties transferDataSource;

    /**
     * 传输策略
     */
    @NestedConfigurationProperty
    private TransferStrategy transferStrategy = new TransferStrategy();

}
