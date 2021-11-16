package io.boncray.common.utils;

import org.springframework.util.CollectionUtils;

import java.util.*;
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
            return new HashMap<>();
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
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(classifier, x -> x));
    }

    /**
     * 集合数据提取
     *
     * @param list       源数据
     * @param classifier 提取数据的function
     * @param <T>        元数据对象类型
     * @param <K>        提取数据类型
     * @return 返回提取后的数据列表
     */
    public static <T, K> List<K> fetchToList(List<T> list, Function<? super T, ? extends K> classifier) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(classifier).collect(Collectors.toList());
    }

    /**
     * 集合数据提取
     *
     * @param list       源数据
     * @param classifier 提取数据的function
     * @param <T>        元数据对象类型
     * @param <K>        提取数据类型
     * @return 返回提取后的数据列表
     */
    public static <T, K> Set<K> fetchToSet(List<T> list, Function<? super T, ? extends K> classifier) {
        if (CollectionUtils.isEmpty(list)) {
            return new HashSet<>();
        }
        return list.stream().map(classifier).collect(Collectors.toSet());
    }


}
