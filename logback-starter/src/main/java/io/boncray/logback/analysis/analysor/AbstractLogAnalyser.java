package io.boncray.logback.analysis.analysor;

import io.boncray.bean.mode.log.Log;
import io.boncray.bean.utils.SpringContext;
import io.boncray.logback.analysis.Analysis;
import io.boncray.logback.transfer.station.LogTransferStation;
import org.springframework.context.ApplicationContext;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/7 01:54
 */
public abstract class AbstractLogAnalyser implements Analysis {

    private LogTransferStation logTransferStation;

    @Override
    public void analyse(Log log) {
        Log targetLog = doAnalyse(log);
        if (targetLog == null) {
            return;
        }
        this.loadFromSpringContext();
        logTransferStation.transfer(targetLog);
    }

    /**
     * 从上下文中加载传输执行器
     */
    private void loadFromSpringContext() {
        ApplicationContext applicationContext = SpringContext.getApplicationContext();
        if (applicationContext != null && logTransferStation == null) {
            logTransferStation = applicationContext.getBean(LogTransferStation.class);
        }
    }

    /**
     * 执行各自解析逻辑
     */
    protected abstract Log doAnalyse(Log source);
}
