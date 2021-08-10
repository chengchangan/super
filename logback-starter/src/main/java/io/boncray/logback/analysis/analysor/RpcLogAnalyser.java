package io.boncray.logback.analysis.analysor;

import cn.hutool.core.util.StrUtil;
import io.boncray.bean.mode.log.Log;
import io.boncray.bean.mode.log.LogType;
import io.boncray.bean.mode.log.NormalLog;
import io.boncray.bean.mode.log.RpcLog;
import io.boncray.logback.analysis.component.LogTempStore;
import io.boncray.logback.filter.LogbackFilter;
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
            // 半成品rpc日志，不输送
            LogTempStore.put(getCacheKey(rpcLogItem), rpcLogItem);
            // 如果是rpc 的开始日志，则使用普通日志记录
            return toNormalLog(rpcLogItem);
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


    /**
     * 将rpc日志保存为normal日志
     */
    private Log toNormalLog(RpcLog rpcLogItem) {
        NormalLog normalLog = new NormalLog();
        normalLog.setLogType(LogType.RPC_LOG.name());
        normalLog.setParentTrackId(rpcLogItem.getParentTrackId());
        normalLog.setCurrentTrackId(rpcLogItem.getCurrentTrackId());
        normalLog.setServiceName(rpcLogItem.getServiceName());
        normalLog.setLoggerName(LogbackFilter.class.getName());
        normalLog.setLevel(rpcLogItem.getLevel());
        normalLog.setMessage("");
        normalLog.setLogTime(rpcLogItem.getLogTime());
        return normalLog;
    }

}
