package io.boncray.example.CommonTest;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.boncray.bean.mode.base.PageList;
import io.boncray.bean.mode.base.PageResult;
import io.boncray.bean.mode.response.Result;
import io.boncray.example.cmdb.Product;
import io.boncray.example.cmdb.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class CommonTest {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/material/list")
    public JSONObject test() {
        String str = "{\"code\":\"1\",\"desc\":\"成功\",\"data\":[{\"code\":\"123\",\"name\":\"测试名称\",\"spec\":\"物料规格\",\"typeCode\":\"类型编码\",\"typeName\":\"类型名称\",\"overStockInRate\":0.4,\"overStockOutRate\":\"0.5\",\"boxQty\":\"2\",\"baseUnitName\":\"件\",\"weight\":2.5,\"volume\":2.1,\"autoWarehouse\":1}]}";
        return JSONUtil.parseObj(str);
    }

    /**
     * 如果返回String类型，需要配置 produces = {"application/json"}
     */
    @GetMapping(value = "/test", produces = {"application/json"})
    public Result<PageResult<Product>> test1() {
        PageList<Product> products = productService.pageList();
        return Result.pageSuccess(products);
    }

}
