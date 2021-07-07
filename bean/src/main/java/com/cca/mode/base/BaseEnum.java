package com.cca.mode.base;

import com.cca.serialize.EnumDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/7 9:48
 */
@JsonDeserialize(using = EnumDeserializer.class)
public interface BaseEnum<K, V> {

    /**
     * 枚举值
     *
     * @return 枚举值
     */
    K code();

    /**
     * 枚举中文描述
     *
     * @return 枚举中文描述
     */
    V msg();
}
