package com.cca.web.generator.generate;

import com.cca.core.util.Assert;
import com.cca.core.util.FileUtil;
import com.cca.core.util.StringUtil;
import com.cca.web.generator.domain.Configuration;
import com.cca.web.generator.domain.Table;
import com.cca.web.generator.domain.TemplateConfig;
import com.cca.web.generator.domain.property.DataBase;
import com.cca.web.generator.generate.impl.MssqlCommentHandler;
import com.cca.web.generator.generate.impl.NameHandlerImpl;
import com.cca.web.generator.generate.impl.TemplateEngineImpl;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/18 17:04
 */
@Slf4j
public class Generator {

    protected Connection conn;
    protected TemplateParse templateEngine;
    protected NameHandler nameHandler;
    protected CommentHandler commentHandler;


    private void init(Configuration config) throws SQLException, ClassNotFoundException {
        DataBase database = config.getDatabase();
        Class.forName(database.getDriverClass());

        Properties properties = new Properties();
        properties.setProperty("user", database.getUser());
        properties.setProperty("password", database.getPassword());
        properties.setProperty("remarks", "true");
        properties.setProperty("useInformationSchema", "true");
        conn = DriverManager.getConnection(database.getUrl(), properties);

        templateEngine = new TemplateEngineImpl();
        nameHandler = new NameHandlerImpl();
        commentHandler = new MssqlCommentHandler();
    }


    public void run(Configuration config) throws SQLException, ClassNotFoundException {
        // 初始化
        init(config);
        Map<String, Table> tableMap = commentHandler.getTableInfo(conn, StringUtil.getDBFromUrl(config.getDatabase().getUrl()), config.getGenerateTableList());

        tableMap.forEach((tableName, table) -> {
            for (TemplateConfig templateConfig : config.getTemplateList()) {

                if (Assert.isTrue(config.isIgnoreControllerCode()) && templateConfig.getTemplatePath().contains("controller")) {
                    log.info("忽略生成 controller：{}", tableName);
                    continue;
                }
                if (Assert.isTrue(config.isIgnoreServiceCode()) && templateConfig.getTemplatePath().contains("serviceImpl")) {
                    log.info("忽略生成 service：{}", tableName);
                    continue;
                }
                if (Assert.isTrue(config.isIgnoreMapperCode()) && templateConfig.getTemplatePath().contains("mapper")) {
                    log.info("忽略生成 mapper：{}", tableName);
                    continue;
                }
                nameHandler.processTableToClass(config, table);
                String content = templateEngine.parseTemplate(config, templateConfig.getTemplatePath(), table);
                String writePath = templateEngine.getWritePath(config.getBasePackage(), templateConfig, table);
                FileUtil.writeFile(writePath, content);
            }
            System.out.println("generate finished table：" + tableName);
        });
    }


}
