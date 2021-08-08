package io.boncray.logback.transfer.channel.mysql;

import io.boncray.core.database.mybatis.SqlSessionDecorator;
import io.boncray.logback.config.DataSourceProperties;
import io.boncray.logback.config.LogBackConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

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


    @Bean
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

    @Bean
    public SqlSessionFactory transferSqlSessionFactory(@Qualifier("transferDataSource") DataSource transferDataSource) throws Exception {
        return createSqlSessionFactory(transferDataSource);
    }

    @Bean
    public SqlSessionDecorator transferSqlSessionDecorator(@Qualifier("transferSqlSessionFactory") SqlSessionFactory transferSqlSessionFactory) {
        return new SqlSessionDecorator(new SqlSessionTemplate(transferSqlSessionFactory));
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
