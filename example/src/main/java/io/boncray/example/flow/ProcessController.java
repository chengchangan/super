package io.boncray.example.flow;

import io.boncray.example.flow.impl.translat.context.TranslateContext;
import io.boncray.flow.trigger.ProcessTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/14 16:01
 */
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessTrigger processTrigger;

    @GetMapping("/translate/create")
    public void translateTest() {
        String bizCode = "translate";
        String operation = "create";
        TranslateContext context = new TranslateContext(bizCode, operation);

        processTrigger.fire(context);
    }

}
