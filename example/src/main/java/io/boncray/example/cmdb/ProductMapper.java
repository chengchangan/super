package io.boncray.example.cmdb;

import io.boncray.bean.mode.base.PageList;
import io.boncray.bean.mode.base.PageQuery;
import io.boncray.generate.supperClass.dao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品主信息表 dao
 *
 * @author code-generator
 * @version 1.0
 * @date 2022-01-29
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product, ProductQuery> {


    PageList<Product> listPage(@Param("ew") PageQuery query, @Param("test") String st);
}