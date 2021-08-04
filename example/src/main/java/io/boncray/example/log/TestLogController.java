package io.boncray.example.log;

import io.boncray.bean.mode.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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


    @GetMapping("/test")
    public Result<String> test(){
        log.info("hahah");
        return Result.success();
    }

}

