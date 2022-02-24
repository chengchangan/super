package io.boncray.bean.exception;

import io.boncray.bean.mode.base.BaseEnum;

/**
 * @author changan
 * @version 1.0
 * @date 2022/2/24 15:44
 */
public class RemoteCallException extends RuntimeException {

    private Long requestId;

    private final int code;

    public RemoteCallException(BaseEnum<Integer, String> baseEnum, Long requestId) {
        super(baseEnum.msg());
        this.code = baseEnum.code();
        this.requestId = requestId;
    }

    public RemoteCallException(int code, String message, Long requestId) {
        super(message);
        this.code = code;
        this.requestId = requestId;
    }

    public RemoteCallException(int code, String message, Long requestId, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.requestId = requestId;
    }

    public int getCode() {
        return code;
    }
}
