package io.boncray.logback.config;

import com.alibaba.druid.pool.DruidDataSource;
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
    private TransferChannel transferChannel = TransferChannel.MYSQL;

    /**
     * mysql数据源
     */
    private DataSourceProperties transferDataSource;


    /**
     * 传输策略
     */
    private TransferStrategy transferStrategy;


    // todo 测试初始化数据
    {
        transferDataSource = new DataSourceProperties();
        transferDataSource.setUsername("root");
        transferDataSource.setPassword("root");
        transferDataSource.setUrl("jdbc:mysql://101.34.35.72:3306/log_center?serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false");
        transferDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        transferStrategy = new TransferStrategy();
        transferStrategy.setAlways(false);
        transferStrategy.setBatchMaxSize(1000);
    }
}
