package io.boncray.logback.transfer.channel.mysql;

import io.boncray.bean.mode.log.LogType;
import io.boncray.core.database.mybatis.SqlSessionDecorator;
import io.boncray.logback.config.DataSourceProperties;
import io.boncray.logback.config.LogBackConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/8 02:13
 */
@Configuration
//@ConditionalOnProperty() // todo 如果是 mysql 则装载此bean
public class MybatisConfig {

    @Autowired
    private LogBackConfiguration configuration;

    /**
     * 根据日志的类型,决定存放的表
     */
    protected static final Map<LogType, String> mapperMethodMapping = new HashMap<>();

    static {
        mapperMethodMapping.put(LogType.NORMAL_LOG, "io.boncray.logback.normalLogMapper.insert");
        mapperMethodMapping.put(LogType.RPC_LOG, "io.boncray.logback.rpcLogMapper.insert");
    }

    public DataSource transferDataSource() {
        DataSourceProperties properties = configuration.getTransferDataSource();
        return DataSourceBuilder.create()
                .type(properties.getType())
                .driverClassName(properties.getDriverClassName())
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getUsername())
                .build();
    }

    public SqlSessionFactory transferSqlSessionFactory() throws Exception {
        return createSqlSessionFactory(transferDataSource());
    }

    @Bean
    public SqlSessionDecorator transferSqlSessionDecorator() throws Exception {
        SqlSessionFactory sqlSessionFactory = transferSqlSessionFactory();
        return new SqlSessionDecorator(new SqlSessionTemplate(sqlSessionFactory));
    }


    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception {
        ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(resourceLoader.getResources("classpath*:mappers/**/*.xml"));
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

}
