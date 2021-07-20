package io.boncray.flow.exception;

import io.boncray.bean.exception.BizException;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/19 13:59
 */
public class FlowProcessException extends BizException {


    public FlowProcessException(int code, String message) {
        super(code, message);
    }

    public FlowProcessException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public FlowProcessException(ProcessExceptionEnum exceptionEnum) {
        this(exceptionEnum.code(), exceptionEnum.msg());
    }

    public FlowProcessException(ProcessExceptionEnum exceptionEnum, Throwable cause) {
        this(exceptionEnum.code(), exceptionEnum.msg(), cause);
    }
}
