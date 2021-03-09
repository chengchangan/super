package com.cca.web.generator;

import com.cca.mode.inner.GenerateDTO;
import com.cca.web.generator.GeneratorApplication;
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
@RestController("ccaGenerator")
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
    public void generate(@RequestBody GenerateDTO genConfig) {
        generatorApplication.generate(genConfig);
    }

}
