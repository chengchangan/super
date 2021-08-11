package io.boncray.common.utils;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/28 10:20
 */
public class CollectionsUtil {

    /**
     * @param list       源数据
     * @param classifier 根据源数据对象的某个函数进行分组
     * @param <T>        源数据类型
     * @param <K>        分组key的类型
     * @return
     */
    public static <T, K> Map<K, List<T>> listToMapList(List<T> list, Function<? super T, ? extends K> classifier) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.groupingBy(classifier));
    }

    /**
     * @param list       源数据
     * @param classifier 　根据源数据对象的某个函数进行分组
     * @param <T>        　源数据类型
     * @param <K>        　分组key的类型
     * @return
     */
    public static <T, K> Map<K, T> listToMap(List<T> list, Function<? super T, ? extends K> classifier) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(classifier, x -> x));
    }
}
