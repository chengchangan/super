package io.boncray.generate.domain;

import lombok.Data;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/18 15:00
 */
@Data
public class TemplateConfig {

    /**
     * 模板路径
     */
    private String templatePath;

    /**
     * 模板生产出的文件路径
     */
    private String filePath;

    /**
     * 文件类型，java 、xml等
     */
    private String fileType;

}
