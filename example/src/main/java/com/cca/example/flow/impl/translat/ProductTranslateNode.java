package com.cca.example.flow.impl.translat;

import com.cca.example.flow.impl.translat.context.TranslateContext;
import com.cca.flow.node.Node;
import com.cca.flow.node.StageNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/14 13:26
 */
@Slf4j
@StageNode(code = "productTranslate")
@Component
public class ProductTranslateNode implements Node<TranslateContext> {


    @Override
    public void execute(TranslateContext context) {
        log.info("商品转化：productTranslate");
    }


}
