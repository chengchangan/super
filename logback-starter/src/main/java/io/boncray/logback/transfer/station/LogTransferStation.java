package io.boncray.logback.transfer.station;

import io.boncray.bean.mode.log.Log;
import io.boncray.logback.config.LogBackConfiguration;
import io.boncray.logback.transfer.station.execute.TransferExecutor;
import org.springframework.stereotype.Component;

/**
 * 传输配置
 * 可增加不同的策略
 *
 * @author cca
 * @version 1.0
 * @date 2021/8/8 02:23
 */
@Component
public class LogTransferStation {


    private final TransferExecutor transferExecutor;

    public LogTransferStation(LogBackConfiguration configuration) {
        transferExecutor = new TransferExecutor(configuration);
    }


    /**
     * 加载策略配置，
     * 传递传输日志
     *
     * @param targetLog
     */
    public void transfer(Log targetLog) {
        transferExecutor.transfer(targetLog);
    }


}
