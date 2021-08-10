package io.boncray.example.log;

import io.boncray.bean.mode.response.Result;
import io.boncray.core.util.JacksonUtil;
import io.boncray.logback.config.LogBackConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/4 14:29
 */
@Slf4j
@RestController
@RequestMapping("/log")
public class TestLogController {

    @Autowired
    private LogBackConfiguration configuration;

    @GetMapping("/test")
    public Result<String> test() {
        log.info("hahah");
        log.debug("heihei");
        int a = 1/0;
        log.info("装载的配置:{}", JacksonUtil.toJson(configuration));
        return Result.success();
    }

}

