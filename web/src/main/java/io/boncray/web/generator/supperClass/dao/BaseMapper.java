package io.boncray.web.generator.supperClass.dao;

import io.boncray.web.generator.supperClass.entity.Query;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/20 15:31
 */
public interface BaseMapper<T, Q extends Query> {

    /**
     * 插入.
     *
     * @param entity 新增对象
     * @return 影响的行数
     */
    int insert(@Param("entity") T entity);

    /**
     * 根据查询条件更新记录
     *
     * @param entity 更新对象
     * @param query  条件
     * @return 影响的行数
     */
    int updateByExample(@Param("entity") T entity, @Param("ew") Q query);

    /**
     * 根据主键更新
     *
     * @param entity 更新实体
     * @param pk     主键
     * @return 影响的行数
     */
    int updateByPk(@Param("entity") T entity, @Param("pk") Long pk);


    /**
     * 根据主键删除记录
     *
     * @param pk 主键
     * @return 影响的行数
     */
    int deleteByPk(@Param("pk") Long pk);

    /**
     * 根据指定条件删除行
     *
     * @param query 条件
     * @return 删除行数
     */
    int deleteByExample(@Param("ew") Q query);

    /**
     * 根据主键查询
     *
     * @param pk     主键
     * @param fields 查询字段
     * @return 返回的对象
     */
    T getByKey(@Param("pk") Long pk, @Param("fields") String fields);

    /**
     * 列表查询
     *
     * @param query 条件
     * @return 返回值
     */
    List<T> list(@Param("ew") Q query);

    Collection<T> listPage(@Param("ew") Q query);


}
