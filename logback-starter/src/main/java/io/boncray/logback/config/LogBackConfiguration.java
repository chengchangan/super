package io.boncray.logback.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
//@EnableConfigurationProperties({LogBackConfiguration.class})
//@ConfigurationProperties(prefix = "jiuxian")
@Configuration
public class LogBackConfiguration {

    /**
     * 是否需要收集
     */
    private boolean collectEnabled;

    /**
     * 传输的方式, todo 先默认写死，后续读取配置
     */
    private TransferChannel transferChannel = TransferChannel.NONE;

    /**
     * mysql数据源
     */
    private DataSourceProperties dataSourceProperties;


}
