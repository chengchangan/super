package io.boncray.bean.exception;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/20 14:50
 */
public class BizException extends RuntimeException {

    private int code;

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

