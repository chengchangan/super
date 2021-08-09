package io.boncray.logback.transfer.station;

import cn.hutool.core.util.BooleanUtil;
import io.boncray.bean.mode.log.Log;
import io.boncray.core.util.JacksonUtil;
import io.boncray.logback.config.LogBackConfiguration;
import io.boncray.logback.transfer.station.execute.TransferExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 传输配置
 * 可增加不同的策略
 *
 * @author cca
 * @version 1.0
 * @date 2021/8/8 02:23
 */
@Slf4j
@Component
public class LogTransferStation {


    private final TransferExecutor transferExecutor;

    private final LogBackConfiguration configuration;

    public LogTransferStation(LogBackConfiguration configuration) {
        log.debug("log configuration :{}", JacksonUtil.toJson(configuration));
        this.configuration = configuration;
        transferExecutor = new TransferExecutor(configuration);
    }


    /**
     * 传输层入口
     * <p>
     * 加载策略配置，
     * 传递传输日志
     *
     * @param targetLog
     */
    public void transfer(Log targetLog) {
        if (BooleanUtil.isFalse(configuration.isCollectEnabled())) {
            return;
        }
        transferExecutor.transfer(targetLog);
    }


}
