package io.boncray.generate;

import cn.hutool.core.util.BooleanUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.zaxxer.hikari.HikariDataSource;
import io.boncray.bean.exception.BizException;
import io.boncray.bean.mode.inner.GenerateDTO;
import io.boncray.common.utils.FileUtil;
import io.boncray.common.utils.JacksonUtil;
import io.boncray.generate.domain.Configuration;
import io.boncray.generate.domain.property.DataBase;
import io.boncray.generate.generate.Generator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class GeneratorApplication implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Value("${spring.datasource.url:#{null}}")
    private String url;
    @Value("${spring.datasource.username:#{null}}")
    private String username;
    @Value("${spring.datasource.password:#{null}}")
    private String password;
    @Value("${spring.datasource.driver-class-name:#{null}}")
    private String driver;
    @Value("${generator.test:false}")
    private Boolean test;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void generate(GenerateDTO generateDTO) {
        //解析命令行
        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option<String> configOperation = parser.addStringOption('c', "config");
        try {
            String configFile = parser.getOptionValue(configOperation, "/configuration.json");
            String json = FileUtil.readFileAsStream(configFile);
            Configuration config = JSON.parseObject(json, Configuration.class);

            parseConfig(config, generateDTO);
            Generator generator = new Generator();
            log.info("generate begin 开始：{}", LocalDateTime.now());
            generator.run(config);
            log.info("all generator finishes ! ! ! ,结束：{}", LocalDateTime.now());
        } catch (Exception e) {
            log.error("生成代码异常，参数：【{}】，异常信息：", JacksonUtil.toJson(generateDTO), e);
            throw new BizException(1000, "代码生成异常");
        }
    }


    /**
     * 从spring 上下文中获取参数
     * 1、多数据源手动指定
     * 2、单数据源yml配置中取
     */
    private void parseConfig(Configuration config, GenerateDTO generateDTO) {
        // 非测试环境，进行数据源自动选择
        if (BooleanUtil.isFalse(test)) {
            setDataSource(config, generateDTO);
        }

        if (StringUtils.isNotEmpty(generateDTO.getBasePackage())) {
            config.setBasePackage(generateDTO.getBasePackage());
        }
        config.setGenerateTableList(generateDTO.getGenerateTableList());
        config.setIgnoreTableFirstWord(Boolean.parseBoolean(generateDTO.getIgnoreTableFirstWord()));
        config.setIgnoreControllerCode(Boolean.parseBoolean(generateDTO.getIgnoreControllerCode()));
        config.setIgnoreServiceCode(Boolean.parseBoolean(generateDTO.getIgnoreServiceCode()));
        config.setIgnoreDaoCode(Boolean.parseBoolean(generateDTO.getIgnoreDaoCode()));
        config.setIgnoreMapperCode(Boolean.parseBoolean(generateDTO.getIgnoreMapperCode()));
    }


    /**
     * 设置生成代码的数据源
     *
     * @param config      代码生成配置
     * @param generateDTO controller 传入的配置
     */
    private void setDataSource(Configuration config, GenerateDTO generateDTO) {
        DataSource dataSource = null;
        if (StringUtils.isNotEmpty(generateDTO.getDataSource())) {
            // 多数据源，指定优先级最高
            dataSource = (DataSource) applicationContext.getBean(generateDTO.getDataSource());
        }

        DataBase dataBase = new DataBase();
        if (dataSource != null) {
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
        } else if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)
                && StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(driver)) {
            dataBase.setUser(username);
            dataBase.setPassword(password);
            dataBase.setUrl(url);
            dataBase.setDriverClass(driver);
        } else {
            config.setDatabase(config.getDatabase());
            return;
//            throw new IllegalArgumentException("暂无发现数据源配置，请配置数据源相关信息重试！");
        }
        config.setDatabase(dataBase);
    }


}
