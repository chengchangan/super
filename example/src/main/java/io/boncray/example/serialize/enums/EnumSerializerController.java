package io.boncray.example.serialize.enums;

import cn.hutool.json.JSONUtil;
import io.boncray.example.serialize.enums.entity.TestQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 测试枚举序列化和反序列化
 *
 * @author cca
 * @version 1.0
 * @date 2021/7/7 9:59
 */
@RestController
@RequestMapping("/serialize")
public class EnumSerializerController {


    @PostMapping("/enum")
    public TestQuery testEnum(@RequestBody TestQuery query) {

        System.out.println(JSONUtil.toJsonStr(query));

        return query;
    }


}
