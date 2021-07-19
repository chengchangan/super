package io.boncray.flow.node;

import cn.hutool.core.util.StrUtil;
import io.boncray.flow.exception.FlowProcessException;
import lombok.Data;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/19 14:13
 */
@Data
public abstract class BaseContext {

    protected String bizCode;

    protected String operation;


    public BaseContext() {
    }

    public BaseContext(String bizCode, String operation) {
        this.bizCode = bizCode;
        this.operation = operation;
    }


    public void check() {
        if (StrUtil.isBlank(bizCode) || StrUtil.isBlank(operation)) {
            throw new FlowProcessException("bizCode or operation is blank");
        }
    }
}
