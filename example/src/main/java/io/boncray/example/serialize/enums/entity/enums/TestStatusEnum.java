package io.boncray.example.serialize.enums.entity.enums;

import io.boncray.bean.mode.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 测试枚举序列化/反序列化
 *
 * @author cca
 * @version 1.0
 * @date 2021/7/7 10:04
 */
public enum TestStatusEnum implements BaseEnum<Integer, String> {

    WAIT(1, "等待"),
    TESTING(2, "测试中"),
    FINISHED(3, "已完成");

    @JsonValue
    private final Integer value;
    private final String msg;

    TestStatusEnum(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    @Override
    public Integer code() {
        return value;
    }

    @Override
    public String msg() {
        return msg;
    }

}
