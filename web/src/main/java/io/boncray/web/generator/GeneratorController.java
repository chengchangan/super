package io.boncray.web.generator;

import io.boncray.bean.mode.inner.GenerateDTO;
import io.boncray.bean.mode.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/21 16:37
 */
@Api(tags = "代码生成器")
@RestController("customGenerator")
@RequestMapping("/gen")
public class GeneratorController {

    @Autowired
    private GeneratorApplication generatorApplication;


    /**
     * 代码生成器入口
     *
     * @param genConfig 配置
     */
    @ApiOperation("代码生成器入口")
    @PostMapping("/java")
    public Result<Boolean> generate(@RequestBody GenerateDTO genConfig) {
        generatorApplication.generate(genConfig);
        return Result.success();
    }

}
