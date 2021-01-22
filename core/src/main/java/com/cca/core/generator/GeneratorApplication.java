package com.cca.core.generator;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.cca.core.generator.domain.Configuration;
import com.cca.core.generator.domain.GenConfig;
import com.cca.core.generator.domain.property.DataBase;
import com.cca.core.generator.generate.Generator;
import com.cca.core.util.Assert;
import com.cca.core.util.FileUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/21 16:33
 */
@Component
public class GeneratorApplication implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void generate(GenConfig genConfig) {
        //解析命令行
        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option<String> configOperation = parser.addStringOption('c', "config");
        try {
            String configFile = parser.getOptionValue(configOperation, "/configuration.json");
            String json = FileUtil.readFileAsStream(configFile);
            Configuration config = JSON.parseObject(json, Configuration.class);

            parseConfig(config, genConfig);
            Generator generator = new Generator();
            System.out.println("generate begin 开始：" + LocalDateTime.now());
            generator.run(config);
            System.out.println("all generator finishes ! ! ! ,结束：" + LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 从spring 上下文中获取参数
     * 1、datasource
     */
    private void parseConfig(Configuration config, GenConfig genConfig) {
        DataSource dataSource;
        if (StringUtils.isNotBlank(genConfig.getDataSource())) {
            // getByName
            dataSource = (DataSource) applicationContext.getBean(genConfig.getDataSource());
        } else {
            // getByType
            dataSource = applicationContext.getBean(DataSource.class);
        }
        DataBase dataBase = new DataBase();
        if (dataSource instanceof HikariDataSource) {
            HikariDataSource hikariDataSource = ((HikariDataSource) dataSource);
            dataBase.setUser(hikariDataSource.getUsername());
            dataBase.setPassword(hikariDataSource.getPassword());
            dataBase.setUrl(hikariDataSource.getJdbcUrl());
            dataBase.setDriverClass(hikariDataSource.getDriverClassName());
        } else if (dataSource instanceof DruidDataSource) {
            DruidDataSource druidDataSource = (DruidDataSource) dataSource;
            dataBase.setUser(druidDataSource.getUsername());
            dataBase.setPassword(druidDataSource.getPassword());
            dataBase.setUrl(druidDataSource.getUrl());
            dataBase.setDriverClass(druidDataSource.getDriverClassName());
        } else {
            throw new IllegalArgumentException("暂不支持数据源：" + dataSource.getClass().getName() + ",请使用[HikariDataSource，DruidDataSource]");
        }
        config.setDatabase(dataBase);
        if (StringUtils.isNotEmpty(genConfig.getBasePackage())) {
            config.setBasePackage(genConfig.getBasePackage());
        }
        config.setGenerateTableList(genConfig.getGenerateTableList());
        config.setIgnoreTableFirstWord(Boolean.TRUE.equals(genConfig.isIgnoreTableFirstWord()));
        config.setIgnoreServiceCode(Assert.isTrue(genConfig.isIgnoreServiceCode()));
    }


}
