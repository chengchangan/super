package io.boncray.logback.collector.impl;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggerContextVO;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.boncray.bean.constants.LogConstant;
import io.boncray.bean.mode.log.LocalLogItem;
import io.boncray.logback.collector.CollectionAble;
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
public class LocalLogCollection implements CollectionAble<LocalLogItem> {

    private static final String SERVICE_NAME_KEY = "APP_NAME";

    @Override
    public boolean isNeedCollection(ILoggingEvent iLoggingEvent) {
        Map<String, String> mdcPropertyMap = iLoggingEvent.getMDCPropertyMap();
        if (!LogbackFilter.class.getName().equals(iLoggingEvent.getLoggerName())) {
            return StrUtil.isNotBlank(mdcPropertyMap.get(LogConstant.PARENT_TRACK_ID))
                    && StrUtil.isNotBlank(mdcPropertyMap.get(LogConstant.CURRENT_TRACK_ID));
        }
        return false;
    }

    @Override
    public LocalLogItem getCollectionData(ILoggingEvent iLoggingEvent) {
        Map<String, String> mdcPropertyMap = iLoggingEvent.getMDCPropertyMap();
        LoggerContextVO contextVO = iLoggingEvent.getLoggerContextVO();

        LocalLogItem item = new LocalLogItem();
        item.setParentTrackId(Long.valueOf(mdcPropertyMap.get(LogConstant.PARENT_TRACK_ID)));
        item.setCurrentTrackId(Long.valueOf(mdcPropertyMap.get(LogConstant.CURRENT_TRACK_ID)));
        item.setServiceName(contextVO.getPropertyMap().get(SERVICE_NAME_KEY));
        item.setLoggerName(iLoggingEvent.getLoggerName());
        item.setLevel(iLoggingEvent.getLevel().toString());
        item.setMessage(iLoggingEvent.getFormattedMessage());
        item.setLogTime(DateUtil.toLocalDateTime(Instant.ofEpochMilli(iLoggingEvent.getTimeStamp())));
        return item;
    }

    @Override
    public void transfer(LocalLogItem data) {
        System.err.println("local 传输数据：" + JSONUtil.toJsonStr(data));
    }
}
