package io.boncray.example.cmdb;

import io.boncray.example.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author changan
 * @version 1.0
 * @date 2022/2/11 17:46
 */
public class PageTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    public void pageListTest(){
        productService.pageList();
    }

}
