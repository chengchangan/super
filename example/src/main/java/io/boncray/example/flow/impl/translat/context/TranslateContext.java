package io.boncray.example.flow.impl.translat.context;

import io.boncray.flow.node.BaseContext;
import lombok.Data;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/14 13:45
 */
@Data
public class TranslateContext extends BaseContext {


    public TranslateContext(String bizCode, String operation) {
        super(bizCode, operation);
    }
}
