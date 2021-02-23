package com.cca.core.generator.supperClass.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体基类
 *
 * @author cca
 * @version 1.0
 * @date 2021/1/18 11:19
 */
@Data
public abstract class BaseDO implements Serializable, Cloneable {

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;


    /**
     * 返回主键
     *
     * @return 表的主键
     */
    @JSONField(serialize = false)
    public abstract Long getPrimaryKey();


    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public <T> T convert(Class<T> clazz) {
        T obj = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(this, obj);
        return obj;
    }

}