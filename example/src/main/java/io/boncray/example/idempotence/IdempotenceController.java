package io.boncray.example.idempotence;

import io.boncray.bean.mode.response.Result;
import io.boncray.core.aspect.idempotence.Idempotence;
import io.boncray.example.flow.param.TranslateParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/idempotence")
public class IdempotenceController {


    @PostMapping("/test")
    @Idempotence(group = "idempotence", key = "#param.userName + #param.nickName", timeout = 3)
    public Result<Boolean> translateTest(@RequestBody TranslateParam param) throws InterruptedException {
        TimeUnit.SECONDS.sleep(4);
        return Result.success();
    }

}
