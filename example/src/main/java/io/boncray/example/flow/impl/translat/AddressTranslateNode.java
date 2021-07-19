package io.boncray.example.flow.impl.translat;

import io.boncray.example.flow.impl.translat.context.TranslateContext;
import io.boncray.flow.node.Node;
import io.boncray.flow.node.StageNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/14 13:26
 */
@Slf4j
@StageNode(code = "addressTranslate")
@Component
public class AddressTranslateNode implements Node<TranslateContext> {


    @Override
    public void execute(TranslateContext context) throws InterruptedException {
        log.info("地址转化：addressTranslate");
        TimeUnit.SECONDS.sleep(2);
    }


}
