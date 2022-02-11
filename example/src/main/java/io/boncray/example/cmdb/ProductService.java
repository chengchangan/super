package io.boncray.example.cmdb;

import io.boncray.bean.mode.base.PageList;
import io.boncray.cmdb.database.intercetp.PageInterceptor;
import io.boncray.common.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author changan
 * @version 1.0
 * @date 2022/2/11 17:42
 */
@Slf4j
@Service
public class ProductService {

    @Order(20)
    @Bean
    PageInterceptor pageInterceptor(){
        return new PageInterceptor();
    }


    @Resource
    private ProductMapper productMapper;


    public void pageList(){
        ProductPageQuery query = new ProductPageQuery();
        query.setPageSize(20);
        query.setPageIndex(1);
        PageList<Product> page = productMapper.listPage(query,"hahahha");
        System.out.println(JacksonUtil.toJson(page));
    }

}
