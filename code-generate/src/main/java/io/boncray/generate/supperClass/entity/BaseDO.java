package io.boncray.generate.supperClass.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.boncray.common.utils.BeanUtil;
import lombok.Data;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime modifyTime;


    /**
     * 返回主键
     *
     * @return 表的主键
     */
    @JsonIgnore
    public abstract Long getPrimaryKey();


    /**
     * 设置主键
     *
     * @param pk 表的主键
     */
    public abstract void setPrimaryKey(Long pk);


    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public <T> T convert(Class<T> clazz) {
        return BeanUtil.convert(this, clazz);
    }

}
