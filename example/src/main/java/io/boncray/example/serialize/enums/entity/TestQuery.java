package io.boncray.example.serialize.enums.entity;

import io.boncray.example.serialize.enums.entity.enums.TestStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/7 10:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestQuery {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "测试枚举")
    private List<TestStatusEnum> statusEnum;

}
