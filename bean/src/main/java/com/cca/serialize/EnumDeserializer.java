package com.cca.serialize;

import com.cca.mode.base.BaseEnum;
import com.cca.utils.EnumUtility;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/7 10:57
 */
public class EnumDeserializer extends JsonDeserializer<BaseEnum> implements ContextualDeserializer {
    private String value;
    private Class rawClass;

    public EnumDeserializer() {
        this("", null);
    }

    public EnumDeserializer(String value, Class<?> rawClass) {
        this.value = value;
        this.rawClass = rawClass;
    }

    @Override
    public BaseEnum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonStreamContext parent = jp.getParsingContext();
        Class currentValueClass = parent.getCurrentValue().getClass();
        String currentName = parent.getCurrentName();
        JsonNode node = jp.getCodec().readTree(jp);
        if (parent.inArray()) {
            currentName = ((JsonReadContext) parent).getParent().getCurrentName();
            BeanUtils.findPropertyType(currentName, new Class[]{parent.getParent().getCurrentValue().getClass()});
            return EnumUtility.getEnumValue(this.rawClass, node.asText());
        } else {
            Class clazz = BeanUtils.findPropertyType(currentName, new Class[]{currentValueClass});
            BaseEnum enumValue = EnumUtility.getEnumValue(clazz, node.asText());
            return enumValue != null ? enumValue : null;
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) {
        if (beanProperty != null) {
            Class<?> rawClass = null;
            if (Objects.equals(beanProperty.getType().getRawClass().getInterfaces()[0], BaseEnum.class)) {
                rawClass = beanProperty.getType().getRawClass();
            }

            if (Objects.equals(beanProperty.getType().getRawClass(), List.class)
                    && beanProperty.getType().getContentType().getInterfaces() != null
                    && beanProperty.getType().getContentType().getInterfaces().size() > 0
                    && Objects.equals(beanProperty.getType().getContentType().getInterfaces().get(0).getRawClass(), BaseEnum.class)) {

                rawClass = beanProperty.getType().getContentType().getRawClass();
            }

            if (rawClass != null) {
                ApiModelProperty apiModelProperty = beanProperty.getAnnotation(ApiModelProperty.class);
                if (apiModelProperty == null) {
                    apiModelProperty = beanProperty.getContextAnnotation(ApiModelProperty.class);
                }

                if (apiModelProperty != null) {
                    return new EnumDeserializer(apiModelProperty.value(), rawClass);
                }

                return new EnumDeserializer(beanProperty.getName(), rawClass);
            }
        }

        return null;
    }
}