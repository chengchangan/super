package io.boncray.logback.analysis.analysor;

import io.boncray.bean.mode.log.Log;
import io.boncray.logback.config.LogType;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/7 01:54
 */
public class DefaultLogAnalyser extends AbstractLogAnalyser {

    @Override
    public LogType supportType() {
        return null;
    }

    @Override
    public Log doAnalyse(Log source) {
        // 默认啥也不干
        return source;
    }
}
