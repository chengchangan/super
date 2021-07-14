package io.boncray.bean.utils;

import io.boncray.bean.mode.base.BaseEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/7 10:42
 */
public class EnumUtility {
    private static final Logger log = LoggerFactory.getLogger(EnumUtility.class);

    public EnumUtility() {
    }

    public static <K, T extends BaseEnum<K, ?>> T getEnumValue(Class<T> enumType, String code) {
        try {
            if (StringUtils.isBlank(code)) {
                return null;
            }
            if (enumType.isEnum()) {
                return Arrays.stream(enumType.getEnumConstants())
                        .filter(x -> x.code().toString().equals(code))
                        .findFirst()
                        .orElse(null);
            }
        } catch (Exception exception) {
            log.error("getEnumValue error", exception);
        }
        return null;
    }
}