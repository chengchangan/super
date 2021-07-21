package io.boncray.example.flow.impl.translat;

import io.boncray.example.flow.impl.translat.context.TranslateContext;
import io.boncray.example.flow.impl.translat.context.TranslateException;
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
    public void execute(TranslateContext context) {
        log.info("地址转化：addressTranslate");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new TranslateException(123, "我睡眠出错了", e);
        }
    }


}
