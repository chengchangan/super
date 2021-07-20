package io.boncray.flow.exception;

import io.boncray.bean.mode.base.BaseEnum;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/20 14:59
 */
public enum ProcessExceptionEnum implements BaseEnum<Integer, String> {

    // 10 node
    FLOW_NODE_100001(100001, "context bizCode or operation is blank"),

    FLOW_NODE_100002(100002, "process.node.code.already.exists："),


    // 11 process
    FLOW_NODE_110001(110001, "process.biz.code.can.not.be.null.or.empty"),
    FLOW_NODE_110002(110002, "process.operation.can.not.be.null.or.empty"),
    FLOW_NODE_110003(110003, "process.node.list.can.not.be.null.or.empty"),
    FLOW_NODE_110004(110004, "trade.duplicate.process.config"),
    FLOW_NODE_110005(110005, "process already exists"),


    // 12 trigger
    FLOW_TRIGGER_120001(120001, "The nodes with bizCode and operation not exists."),
    FLOW_TRIGGER_120002(120002, "process node execute failed"),
    ;


    private final Integer code;
    private final String msg;

    ProcessExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer code() {
        return this.code;
    }

    @Override
    public String msg() {
        return this.msg;
    }
}
