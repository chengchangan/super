package io.boncray.example.cmdb;

import io.boncray.bean.mode.base.PageList;
import io.boncray.cmdb.database.intercepter.PageInterceptor;
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


    @Resource
    private ProductMapper productMapper;


    public PageList<Product> pageList(){
        ProductPageQuery query = new ProductPageQuery();
        query.setTitle("erer");
        query.setPageSize(20);
        query.setPageIndex(1);
        PageList<Product> page = productMapper.listPage(query,"hahahha");
        System.out.println(JacksonUtil.toJson(page));
        return page;
    }

    public static void main(String[] args) {

    }
}
