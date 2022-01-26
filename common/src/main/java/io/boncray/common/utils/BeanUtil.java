package io.boncray.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cca
 * @version 1.0
 * @date 2021/3/3 19:47
 */
public class BeanUtil {
    private BeanUtil() {
    }

    public static <T> T convert(Object object, Class<T> clazz) {
        if (object == null) {
            return null;
        }
        T obj = BeanUtils.instantiateClass(clazz);
        // 使用糊涂工具包的深拷贝
        cn.hutool.core.bean.BeanUtil.copyProperties(object, obj);
        return obj;
    }

    public static <T> List<T> convertList(List<?> objects, Class<T> clazz) {
        if (CollectionUtil.isEmpty(objects)) {
            return Collections.emptyList();
        }
        return objects.stream().map(x -> convert(x, clazz)).collect(Collectors.toList());
    }


}
