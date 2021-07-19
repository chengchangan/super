package io.boncray.flow.exception;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/19 13:59
 */
public class FlowProcessException extends RuntimeException {


    public FlowProcessException(String message) {
        super(message);
    }

    public FlowProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlowProcessException(Throwable cause) {
        super(cause);
    }


}
