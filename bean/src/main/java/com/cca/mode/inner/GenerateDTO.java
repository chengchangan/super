package com.cca.mode.inner;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/21 16:44
 */
@Data
public class GenerateDTO {

    @ApiModelProperty("包根路径，例如：com.cca")
    private String basePackage;

    @ApiModelProperty(value = "数据源名称")
    private String dataSource;

    @ApiModelProperty("哪些表参与生成，为空：生成所有表")
    private List<String> generateTableList;

    @ApiModelProperty("是否忽略表的第一个字符【true：是，false：否】，例如：t_user　＝>　user")
    private String ignoreTableFirstWord;

    @ApiModelProperty("是否忽略生成service代码【true：是，false：否】")
    private String ignoreServiceCode;

}
