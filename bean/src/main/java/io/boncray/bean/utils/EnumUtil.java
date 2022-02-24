package io.boncray.bean.utils;

import cn.hutool.core.util.StrUtil;
import io.boncray.bean.mode.base.BaseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/7 10:42
 */
public class EnumUtil {
    private static final Logger log = LoggerFactory.getLogger(EnumUtil.class);

    public EnumUtil() {
    }

    public static <K, T extends BaseEnum<K, ?>> T getEnumValue(Class<T> enumType, K code) {
        try {
            if (code == null) {
                return null;
            }
            if (code instanceof String && StrUtil.isBlank((String) code)) {
                return null;
            }
            if (enumType.isEnum()) {
                return Arrays.stream(enumType.getEnumConstants())
                        .filter(x -> x.code().toString().equals(code.toString()))
                        .findFirst()
                        .orElse(null);
            }
        } catch (Exception exception) {
            log.error("getEnumValue error", exception);
        }
        return null;
    }
}