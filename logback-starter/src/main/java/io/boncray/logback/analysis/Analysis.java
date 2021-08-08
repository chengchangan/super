package io.boncray.logback.analysis;

import io.boncray.bean.mode.log.Log;
import io.boncray.bean.mode.log.LogType;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/7 01:54
 */
public interface Analysis {

    /**
     * 处理日志的类型
     *
     * @return 处理日志的类型
     */
    LogType supportType();

    /**
     * 分析日志
     *
     * @param source
     */
    void analyse(Log source);

}
