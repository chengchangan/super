package io.boncray.bean.mode.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author changan
 * @version 1.0
 * @date 2022/2/11 14:39
 */
@Data
public class PageQuery {

    @ApiModelProperty(value = "每页数据")
    private Integer pageSize;

    @ApiModelProperty(value = "目标页码")
    private Integer pageIndex;

}
