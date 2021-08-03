package io.boncray.logback.factory;

import io.boncray.logback.logger.TrackLogger;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/3 15:03
 */
public class TrackLoggerFactory {


    private static TrackLogger getTrackLogger(Class<?> clazz) {
        return new TrackLogger(clazz);
    }

    private static TrackLogger getTrackLogger(String clazzName) {
        return new TrackLogger(clazzName);
    }

}
