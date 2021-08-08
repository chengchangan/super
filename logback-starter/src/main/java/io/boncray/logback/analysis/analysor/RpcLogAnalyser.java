package io.boncray.logback.analysis.analysor;

import cn.hutool.core.util.StrUtil;
import io.boncray.bean.mode.log.Log;
import io.boncray.bean.mode.log.LogType;
import io.boncray.bean.mode.log.RpcLog;
import io.boncray.logback.analysis.component.LogTempStore;
import org.springframework.stereotype.Component;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/7 02:54
 */
@Component
public class RpcLogAnalyser extends AbstractLogAnalyser {

    @Override
    public LogType supportType() {
        return LogType.RPC_LOG;
    }

    @Override
    protected Log doAnalyse(Log source) {
        RpcLog rpcLogItem = (RpcLog) source;
        if (StrUtil.isBlank(rpcLogItem.getResponseData()) && StrUtil.isNotBlank(rpcLogItem.getRequestPath())) {
            // 半成品日志，不输送
            LogTempStore.put(getCacheKey(rpcLogItem), rpcLogItem);
            return null;
        } else {
            RpcLog startRpcLog = LogTempStore.getAndRemove(getCacheKey(rpcLogItem));
            rpcLogItem.setMethod(startRpcLog.getMethod());
            rpcLogItem.setRequestPath(startRpcLog.getRequestPath());
            rpcLogItem.setRequestParam(startRpcLog.getRequestParam());
            rpcLogItem.setLogTime(startRpcLog.getLogTime());
            return rpcLogItem;
        }
    }

    private String getCacheKey(RpcLog rpcLogItem) {
        return rpcLogItem.getParentTrackId() + "_" + rpcLogItem.getCurrentTrackId();
    }
}
