package com.cca.core.generator.generate.impl;


import com.cca.core.constant.Constants;
import com.cca.core.generator.domain.Configuration;
import com.cca.core.generator.domain.Table;
import com.cca.core.generator.domain.TemplateConfig;
import com.cca.core.generator.generate.TemplateParse;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.time.LocalDate;
import java.util.Properties;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/18 11:20
 */
public class TemplateEngineImpl implements TemplateParse {

    /**
     * init
     */
    public TemplateEngineImpl() {
        Properties p = new Properties();
        try {
            // 加载classpath目录下的vm文件
            p.setProperty("file.resource.loader.class", ClasspathResourceLoader.class.getName());
            // 定义字符集
            p.setProperty(Velocity.ENCODING_DEFAULT, Constants.UTF8);
            p.setProperty(Velocity.OUTPUT_ENCODING, Constants.UTF8);
            // 初始化Velocity引擎，指定配置Properties

            Velocity.init(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String parseTemplate(Configuration config, String templatePath, Table table) {
        Template template = Velocity.getTemplate(templatePath, Constants.UTF8);
        VelocityContext context = createContext();
        context.put("table", table);
        context.put("context", config);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

    @Override
    public String getWritePath(String basePackage, TemplateConfig templateConfig, Table table) {
        StringBuilder builder = new StringBuilder(System.getProperty("user.dir")).append("\\src.main\\");
        String path = getPath(templateConfig.getFilePath(), basePackage, table.getModule(), table.getClassName());
        String fullPath = builder.append(path).toString();
        return fullPath.replace(".", "\\") + "." + templateConfig.getFileType();
    }

    private String getPath(String filePath, String basePackage, String module, String fileName) {
        if (StringUtils.isEmpty(module)) {
            filePath = filePath.replace(".${moduleName}", "");
        }
        VelocityContext context = new VelocityContext();
        context.put("basePackage", basePackage);
        context.put("moduleName", module);
        context.put("fileName", fileName);
        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "", filePath);
        return writer.toString();
    }

    private VelocityContext createContext() {
        VelocityContext context = new VelocityContext();
        context.put("date", LocalDate.now());
        return context;
    }
}
