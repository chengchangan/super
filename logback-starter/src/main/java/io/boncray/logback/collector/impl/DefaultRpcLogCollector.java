package io.boncray.logback.collector.impl;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggerContextVO;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.boncray.bean.constants.LogConstant;
import io.boncray.bean.mode.log.LogType;
import io.boncray.bean.mode.log.RpcLog;
import io.boncray.common.utils.JacksonUtil;
import io.boncray.logback.collector.Collectable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/6 16:47
 */
@Component
public class DefaultRpcLogCollector implements Collectable<RpcLog> {

    private static final String SERVICE_NAME_KEY = "APP_NAME";

    @Override
    public boolean isNeedCollect(ILoggingEvent iLoggingEvent) {
        Object[] argumentArray = iLoggingEvent.getArgumentArray();

        boolean isNeed = firstArgIsRpc(argumentArray);
        if (isNeed) {
            return isNeed(iLoggingEvent);
        }
        return false;
    }

    /**
     * rpc日志
     */
    private boolean firstArgIsRpc(Object[] argumentArray) {
        if (argumentArray == null) {
            return false;
        }
        if (argumentArray.length > 1 && LogType.RPC_LOG.name().equals(argumentArray[0].toString())) {
            return true;
        }
        return false;
    }

    public boolean isNeed(ILoggingEvent iLoggingEvent) {
        return true;
    }

    @Override
    public RpcLog collectData(ILoggingEvent iLoggingEvent) {
        Map<String, String> mdcPropertyMap = iLoggingEvent.getMDCPropertyMap();
        LoggerContextVO contextVO = iLoggingEvent.getLoggerContextVO();
        Object[] logArgs = iLoggingEvent.getArgumentArray();

        RpcLog item = new RpcLog();
        item.setParentTrackId(Long.valueOf(mdcPropertyMap.get(LogConstant.PARENT_TRACK_ID)));
        item.setCurrentTrackId(Long.valueOf(mdcPropertyMap.get(LogConstant.CURRENT_TRACK_ID)));
        String serviceName = contextVO.getPropertyMap().get(SERVICE_NAME_KEY);
        if (StrUtil.isBlank(serviceName)) {
            serviceName = "default";
        }
        item.setServiceName(serviceName);
        item.setLevel(iLoggingEvent.getLevel().toString());
        this.parseLogArg(logArgs, item);
        item.setLogTime(DateUtil.toLocalDateTime(Instant.ofEpochMilli(iLoggingEvent.getTimeStamp())));
        return item;
    }

    /**
     * 解析日志里的参数
     */
    private void parseLogArg(Object[] logArgs, RpcLog item) {
        if ("start".equals(logArgs[1].toString())) {
            Map<String, Object> requestParam = new HashMap<>(4);
            requestParam.put("body", logArgs[4]);
            requestParam.put("header", logArgs[5]);
            item.setMethod(logArgs[2].toString());
            item.setRequestPath(logArgs[3].toString());
            item.setRequestParam(JacksonUtil.toJson(requestParam));
        } else if ("end".equals(logArgs[1].toString())) {
            item.setElapsedTime(Long.valueOf(logArgs[2].toString()));
            item.setResponseData(logArgs[3].toString());
        }
    }

}
