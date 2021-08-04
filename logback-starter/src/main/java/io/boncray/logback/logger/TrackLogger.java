package io.boncray.logback.logger;

import io.boncray.bean.constants.LogConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/3 15:11
 */
public class TrackLogger extends AbstractLogger {


    private final Logger logger;

    public TrackLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public TrackLogger(String clazzName) {
        this.logger = LoggerFactory.getLogger(clazzName);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }


    @Override
    protected void putMDC() {

    }

    @Override
    protected void cleanMDC() {
        MDC.remove(LogConstant.TRACK_ID);
    }


}
