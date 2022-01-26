package io.boncray.bean.exception;

import io.boncray.bean.mode.base.BaseEnum;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/20 14:50
 */
public class BizException extends RuntimeException {

    private final int code;

    public BizException(BaseEnum<Integer, String> baseEnum) {
        super(baseEnum.msg());
        this.code = baseEnum.code();
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

