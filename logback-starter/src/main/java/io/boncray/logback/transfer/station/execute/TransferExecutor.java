package io.boncray.logback.transfer.station.execute;

import cn.hutool.core.util.BooleanUtil;
import io.boncray.bean.mode.log.Log;
import io.boncray.logback.config.LogBackConfiguration;
import io.boncray.logback.config.TransferStrategy;
import io.boncray.logback.transfer.TransferFactory;
import io.boncray.logback.transfer.Transferable;
import io.boncray.logback.transfer.station.LogCacheStore;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/8 17:23
 */
@Slf4j
public class TransferExecutor {

    private final LogBackConfiguration configuration;
    private final TransferStrategy transferStrategy;

    private final LogCacheStore logCacheStore;

    /**
     * 构建传输器，需要传输配置信息
     */
    public TransferExecutor(LogBackConfiguration configuration) {
        this.configuration = configuration;
        transferStrategy = configuration.getTransferStrategy();
        logCacheStore = new LogCacheStore(configuration, this);
    }

    /**
     * 根据策略传输
     */
    public void transfer(Log targetLog) {
        if (BooleanUtil.isTrue(transferStrategy.getAlways())) {
            doTransfer(targetLog);
        } else {
            logCacheStore.putLog(targetLog);
        }
    }


    /**
     * 直接传输
     */
    public void doTransfer(Object targetLog) {
        Transferable transferor = TransferFactory.getTransferor(configuration.getTransferChannel());
        try {
            transferor.transfer(targetLog);
        } catch (Exception e) {
            log.warn("日志传输失败:", e);
        }

    }

}
