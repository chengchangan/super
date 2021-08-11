package io.boncray.common.utils;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/9 10:05
 */
public class JacksonUtil {
    static ObjectMapper mapper = new ObjectMapper();

    public JacksonUtil() {
    }

    static {
//        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // null value 序列化后，没有此key
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }


    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException exception) {
            throw new JacksonSerializationException(obj.getClass().getSimpleName(), exception);
        }
    }

    public static byte[] toJsonBytes(Object obj) {
        try {
            return StrUtil.utf8Bytes(mapper.writeValueAsString(obj));
        } catch (JsonProcessingException exception) {
            throw new JacksonSerializationException(obj.getClass().getName(), exception);
        }
    }

    public static <T> T toObj(byte[] json, Class<T> cls) {
        try {

            return toObj(StrUtil.utf8Str(json), cls);
        } catch (Exception exception) {
            throw new JacksonDeserializationException(cls.getName(), exception);
        }
    }

    public static <T> T toObj(byte[] json, Type cls) {
        try {
            return toObj(StrUtil.utf8Str(json), cls);
        } catch (Exception exception) {
            throw new JacksonDeserializationException(exception);
        }
    }

    public static <T> T toObj(InputStream inputStream, Class<T> cls) {
        try {
            return mapper.readValue(inputStream, cls);
        } catch (IOException exception) {
            throw new JacksonDeserializationException(exception);
        }
    }

    public static <T> T toObj(byte[] json, TypeReference<T> typeReference) {
        try {
            return toObj(StrUtil.utf8Str(json), typeReference);
        } catch (Exception exception) {
            throw new JacksonDeserializationException(exception);
        }
    }

    public static <T> T toObj(String json, Class<T> cls) {
        try {
            return mapper.readValue(json, cls);
        } catch (IOException var3) {
            throw new JacksonDeserializationException(cls.getName(), var3);
        }
    }

    public static <T> T toObj(String json, Type type) {
        try {
            return mapper.readValue(json, mapper.constructType(type));
        } catch (IOException var3) {
            throw new JacksonDeserializationException(var3);
        }
    }

    public static <T> T toObj(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException var3) {
            throw new JacksonDeserializationException(typeReference.getClass().getName(), var3);
        }
    }

    public static <T> T toObj(InputStream inputStream, Type type) {
        try {
            return mapper.readValue(inputStream, mapper.constructType(type));
        } catch (IOException var3) {
            throw new JacksonDeserializationException(type.getTypeName(), var3);
        }
    }

    public static JsonNode toObj(String json) {
        try {
            return mapper.readTree(json);
        } catch (IOException var2) {
            throw new JacksonDeserializationException(var2);
        }
    }

    public static void registerSubtype(Class<?> clz, String type) {
        mapper.registerSubtypes(new NamedType[]{new NamedType(clz, type)});
    }

    public static ObjectNode createEmptyJsonNode() {
        return new ObjectNode(mapper.getNodeFactory());
    }

    public static ArrayNode createEmptyArrayNode() {
        return new ArrayNode(mapper.getNodeFactory());
    }

    public static JsonNode transferToJsonNode(Object obj) {
        return mapper.valueToTree(obj);
    }

    public static JavaType constructJavaType(Type type) {
        return mapper.constructType(type);
    }


    private static class JacksonSerializationException extends RuntimeException {
        public JacksonSerializationException() {
        }

        public JacksonSerializationException(String message, Throwable cause) {
            super(message, cause);
        }
    }


    private static class JacksonDeserializationException extends RuntimeException {
        public JacksonDeserializationException() {
        }

        public JacksonDeserializationException(Throwable cause) {
            super(cause);
        }

        public JacksonDeserializationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
