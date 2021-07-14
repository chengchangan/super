package io.boncray.example.flow.impl.translat;

import io.boncray.example.flow.impl.translat.context.TranslateContext;
import io.boncray.flow.node.Node;
import io.boncray.flow.node.StageNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/14 13:26
 */
@Slf4j
@StageNode(code = "initTranslate")
@Component
public class InitTranslateNode implements Node<TranslateContext> {


    @Override
    public void execute(TranslateContext context) {
        log.info("translate init context");
    }


}
