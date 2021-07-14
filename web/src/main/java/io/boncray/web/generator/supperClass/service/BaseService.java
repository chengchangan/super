package io.boncray.web.generator.supperClass.service;

import io.boncray.web.generator.supperClass.entity.BaseDO;
import io.boncray.web.generator.supperClass.entity.Query;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/21 14:18
 */
public interface BaseService<T extends BaseDO, Q extends Query> {

    /**
     * 根据主键新增或保存
     *
     * @param entity 实体
     * @return
     */
    int insertOrUpdate(T entity);

    /**
     * 新写入数据库记录.
     *
     * @param entity 参数
     * @return 影响的记录数
     */
    int insert(T entity);

    /**
     * 根据主键来更新符合条件的数据库记录.
     *
     * @param entity 更新实体
     * @return 影响的记录数
     */
    int updateByPk(T entity);

    /**
     * 根据主键删除记录
     *
     * @param pk 主键
     * @return 影响的行数
     */
    int deleteByPk(Long pk);

    /**
     * 根据实体查询删除.
     *
     * @param q 条件对象
     * @return 影响的记录数
     */
    int deleteByExample(Q q);

    /**
     * 根据主键查询数据.
     *
     * @param id 主键
     * @return 实体对象
     */
    T getByKey(Long id);

    /**
     * 根据主键查询数据.
     *
     * @param id     主键
     * @param fields 查询字段
     * @return 实体对象
     */
    T getByKey(Long id, String fields);

    /**
     * 根据示例获取单条数据.
     *
     * @param q 查询条件对象
     * @return 实体对象
     */
    T getByExample(Q q);

    /**
     * 根据条件查询实体对象.
     *
     * @param q 查询条件对象
     * @return 实体对象列表
     */
    List<T> listExample(Q q);

    /**
     * 根据条件查询实体对象返回分页结果.
     *
     * @param q        参数
     * @param page     第几页
     * @param pageSize 页大小
     * @return 分页的实体对象列表
     */
//    PageList<T> listPage(Q q, int page, int pageSize);


    /**
     * 根据条件查询返回指定的列.
     *
     * @param fields 查询字段
     * @param q      查询条件
     * @return 分页的实体对象列表
     */
    List<T> listFields(String fields, Q q);

    /**
     * 根据条件查询返回指定的列, 返回分页结果.
     *
     * @param fields   查询字段
     * @param q        查询条件
     * @param page     第几页
     * @param pageSize 页大小
     * @return 分页的实体对象列表
     */
//    PageList<T> listFieldsPage(String fields, Q q, int page, int pageSize);

}
