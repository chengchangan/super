package io.boncray.logback.analysis.analysor;

import io.boncray.bean.mode.log.Log;
import io.boncray.bean.utils.SpringContext;
import io.boncray.logback.analysis.Analysis;
import io.boncray.logback.config.LogBackConfiguration;
import io.boncray.logback.config.TransferChannel;
import io.boncray.logback.transfer.Transferable;
import io.boncray.logback.transfer.TransferFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/7 01:54
 */
public abstract class AbstractLogAnalyser implements Analysis {

    private TransferChannel transferChannel;

    @Override
    public void analyse(Log log) {
        Log targetLog = doAnalyse(log);
        if (targetLog == null) {
            return;
        }
        loadFromSpringContext();
        Transferable transferor = TransferFactory.getTransferor(transferChannel);
        transferor.transfer(targetLog);
    }

    private void loadFromSpringContext() {
        ApplicationContext applicationContext = SpringContext.getApplicationContext();
        if (applicationContext != null && transferChannel == null) {
            transferChannel = applicationContext.getBean(LogBackConfiguration.class).getTransferChannel();
        }
    }

    /**
     * 执行各自解析逻辑
     */
    protected abstract Log doAnalyse(Log source);
}
