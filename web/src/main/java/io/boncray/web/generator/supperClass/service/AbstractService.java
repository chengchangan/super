package io.boncray.web.generator.supperClass.service;

import cn.hutool.core.collection.CollectionUtil;
import io.boncray.core.sequence.IdGenerator;
import io.boncray.web.generator.supperClass.dao.BaseMapper;
import io.boncray.web.generator.supperClass.entity.BaseDO;
import io.boncray.web.generator.supperClass.entity.Query;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/21 14:40
 */
public abstract class AbstractService<T extends BaseDO, Q extends Query> implements BaseService<T, Q> {

    @Resource
    private IdGenerator idGenerator;


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
        this.initEntity(entity);
        if (entity.getPrimaryKey() == null) {
            entity.setPrimaryKey(idGenerator.next());
        }
        entity.setCreateTime(LocalDateTime.now());
        entity.setModifyTime(LocalDateTime.now());
        return getMapper().insert(entity);
    }

    @Override
    public int updateByPk(T entity) {
        entity.setModifyTime(LocalDateTime.now());
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
        return this.getByKey(id, "");
    }

    @Override
    public T getByKey(Long id, String fields) {
        return getMapper().getByKey(id, fields);
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

    public void initEntity(T entity) {
    }

    public IdGenerator defaultIdGenerator() {
        return this.idGenerator;
    }

}
