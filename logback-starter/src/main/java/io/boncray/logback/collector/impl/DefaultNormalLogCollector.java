package io.boncray.logback.collector.impl;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggerContextVO;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.boncray.bean.constants.LogConstant;
import io.boncray.bean.mode.log.LogType;
import io.boncray.bean.mode.log.NormalLog;
import io.boncray.logback.collector.Collectable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/6 16:47
 */
@Component
public class DefaultNormalLogCollector implements Collectable<NormalLog> {

    private static final String SERVICE_NAME_KEY = "APP_NAME";

    @Override
    public boolean isNeedCollect(ILoggingEvent iLoggingEvent) {
        Object[] argumentArray = iLoggingEvent.getArgumentArray();

        if (firstArgIsRpc(argumentArray)) {
            // rpc日志
            return false;
        }

        return isNeed(iLoggingEvent);
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
    public NormalLog collectData(ILoggingEvent iLoggingEvent) {
        Map<String, String> mdcPropertyMap = iLoggingEvent.getMDCPropertyMap();
        LoggerContextVO contextVO = iLoggingEvent.getLoggerContextVO();

        NormalLog item = new NormalLog();
        item.setLogType(LogType.NORMAL_LOG.name());
        item.setParentTrackId(Long.valueOf(mdcPropertyMap.get(LogConstant.PARENT_TRACK_ID)));
        item.setCurrentTrackId(Long.valueOf(mdcPropertyMap.get(LogConstant.CURRENT_TRACK_ID)));
        item.setServiceName(contextVO.getPropertyMap().get(SERVICE_NAME_KEY));
        item.setLoggerName(iLoggingEvent.getLoggerName());
        item.setLevel(iLoggingEvent.getLevel().toString());
        this.parseExceptionMsg(iLoggingEvent, item);
        item.setLogTime(DateUtil.toLocalDateTime(Instant.ofEpochMilli(iLoggingEvent.getTimeStamp())));
        return item;
    }

    private void parseExceptionMsg(ILoggingEvent iLoggingEvent, NormalLog item) {
        if (iLoggingEvent.getThrowableProxy() == null) {
            item.setMessage(iLoggingEvent.getFormattedMessage());
            return;
        }
        Map<String, Object> messageMap = new LinkedHashMap<>();
        messageMap.put("messageInfo", iLoggingEvent.getFormattedMessage());
        JSONObject node = JSONUtil.parseObj(iLoggingEvent.getThrowableProxy());
        node.forEach(messageMap::put);
        item.setMessage(JSONUtil.toJsonPrettyStr(messageMap));
    }

}
