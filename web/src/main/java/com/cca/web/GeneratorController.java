package com.cca.web;

import com.cca.core.generator.GeneratorApplication;
import com.cca.core.generator.domain.GenConfig;
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
    @PostMapping("/java")
    public void generate(@RequestBody GenConfig genConfig) {
        generatorApplication.generate(genConfig);
    }

}
