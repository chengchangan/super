package io.boncray.example.flow.impl.translat.context;

import io.boncray.bean.exception.BizException;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/20 16:30
 */
public class TranslateException extends BizException {


    public TranslateException(int code, String message) {
        super(code, message);
    }

    public TranslateException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

}
