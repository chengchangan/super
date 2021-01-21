package com.cca.core.generator.supperClass.service;

import cn.hutool.core.collection.CollectionUtil;
import com.cca.core.generator.supperClass.dao.BaseMapper;
import com.cca.core.generator.supperClass.entity.BaseDO;
import com.cca.core.generator.supperClass.entity.Query;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/21 14:40
 */
public abstract class AbstractService<T extends BaseDO, Q extends Query> implements BaseService<T, Q> {

    @Override
    public int insertOrUpdate(T entity) {
        if (entity.getPrimaryKey() == null) {
            return this.insert(entity);
        } else {
            return this.updateByPk(entity);
        }
    }

    @Override
    public int insert(T entity) {
        return getMapper().insert(entity);
    }

    @Override
    public int updateByPk(T entity) {
        return getMapper().updateByPk(entity, entity.getPrimaryKey());
    }

    @Override
    public int deleteByPk(Long pk) {
        return getMapper().deleteByPk(pk);
    }

    @Override
    public int deleteByExample(Q q) {
        return getMapper().deleteByExample(q);
    }

    @Override
    public T getByKey(Long id) {
        return getMapper().getByKey(id);
    }

    @Override
    public T getByExample(Q q) {
        List<T> list = this.listExample(q);
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<T> listExample(Q q) {
        return getMapper().list(q);
    }

    @Override
    public List<T> listFields(String fields, Q q) {
        return null;
    }

    protected abstract BaseMapper<T, Q> getMapper();
}
