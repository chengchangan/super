package com.cca.web.generator;

import java.util.HashMap;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/18 13,39
 */
public class TypeConvert {

    static HashMap<String, String> TYPE_MAP = new HashMap<>();
    static HashMap<String, String> TYPE_CLASS_MAP = new HashMap<>();

    static {
        TYPE_MAP.put("bit", "Boolean");
        TYPE_MAP.put("date", "LocalDate");
        TYPE_MAP.put("timestamp", "LocalDateTime");
        TYPE_MAP.put("datetime", "LocalDateTime");
        TYPE_MAP.put("varchar", "String");
        TYPE_MAP.put("nvarchar", "String");
        TYPE_MAP.put("mediumtext", "String");
        TYPE_MAP.put("char", "String");
        TYPE_MAP.put("bigint", "Long");
        TYPE_MAP.put("bigint unsigned", "Long");
        TYPE_MAP.put("int", "Integer");
        TYPE_MAP.put("int unsigned", "Integer");
        TYPE_MAP.put("double", "Double");
        TYPE_MAP.put("double unsigned", "Double");
        TYPE_MAP.put("decimal", "Double");
        TYPE_MAP.put("decimal unsigned", "Double");
        TYPE_MAP.put("tinyint", "Integer");
        TYPE_MAP.put("tinyint unsigned", "Integer");

        TYPE_CLASS_MAP.put("LocalDate", "java.time.LocalDate");
        TYPE_CLASS_MAP.put("LocalDateTime", "java.time.LocalDateTime");

    }


    public static String getType(String type) {
        for (String key : TypeConvert.TYPE_MAP.keySet()) {
            if (key.contains(type)) {
                return TypeConvert.TYPE_MAP.get(type);
            }
        }
        return "String";
    }

    public static String getTypeClass(String javaType) {
        return TYPE_CLASS_MAP.get(javaType);
    }
}
