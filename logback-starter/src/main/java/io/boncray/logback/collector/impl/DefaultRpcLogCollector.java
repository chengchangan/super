package io.boncray.logback.collector.impl;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggerContextVO;
import cn.hutool.core.date.DateUtil;
import io.boncray.bean.constants.LogConstant;
import io.boncray.bean.mode.log.LogType;
import io.boncray.bean.mode.log.RpcLog;
import io.boncray.core.util.JacksonUtil;
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
        if (argumentArray.length > 1 && LogType.RPC_LOG.toString().equals(argumentArray[0].toString())) {
            return isNeed(iLoggingEvent);
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
        item.setServiceName(contextVO.getPropertyMap().get(SERVICE_NAME_KEY));
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
