package io.boncray.logback.collector.impl;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggerContextVO;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.boncray.bean.constants.LogConstant;
import io.boncray.bean.mode.log.LocalLog;
import io.boncray.logback.collector.Collectable;
import io.boncray.logback.filter.LogbackFilter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/6 16:47
 */
@Component
public class DefaultLocalLogCollector implements Collectable<LocalLog> {

    private static final String SERVICE_NAME_KEY = "APP_NAME";

    @Override
    public boolean isNeedCollect(ILoggingEvent iLoggingEvent) {
        Map<String, String> mdcPropertyMap = iLoggingEvent.getMDCPropertyMap();
        boolean isNeed = false;
        if (!LogbackFilter.class.getName().equals(iLoggingEvent.getLoggerName())) {
            isNeed = StrUtil.isNotBlank(mdcPropertyMap.get(LogConstant.PARENT_TRACK_ID))
                    && StrUtil.isNotBlank(mdcPropertyMap.get(LogConstant.CURRENT_TRACK_ID));
        }
        return isNeed ? isNeed(iLoggingEvent) : isNeed;
    }

    public boolean isNeed(ILoggingEvent iLoggingEvent) {
        return true;
    }

    @Override
    public LocalLog collectData(ILoggingEvent iLoggingEvent) {
        Map<String, String> mdcPropertyMap = iLoggingEvent.getMDCPropertyMap();
        LoggerContextVO contextVO = iLoggingEvent.getLoggerContextVO();

        LocalLog item = new LocalLog();
        item.setParentTrackId(Long.valueOf(mdcPropertyMap.get(LogConstant.PARENT_TRACK_ID)));
        item.setCurrentTrackId(Long.valueOf(mdcPropertyMap.get(LogConstant.CURRENT_TRACK_ID)));
        item.setServiceName(contextVO.getPropertyMap().get(SERVICE_NAME_KEY));
        item.setLoggerName(iLoggingEvent.getLoggerName());
        item.setLevel(iLoggingEvent.getLevel().toString());
        item.setMessage(iLoggingEvent.getFormattedMessage());
        item.setLogTime(DateUtil.toLocalDateTime(Instant.ofEpochMilli(iLoggingEvent.getTimeStamp())));
        return item;
    }

}
