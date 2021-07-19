package io.boncray.core.aspect.idempotence;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/19 16:56
 */
public class IdempotenceTimeOutException extends RuntimeException {

    public IdempotenceTimeOutException(String message) {
        super(message);
    }

    public IdempotenceTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }
}
