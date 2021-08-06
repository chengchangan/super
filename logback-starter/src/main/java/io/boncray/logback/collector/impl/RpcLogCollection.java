package io.boncray.logback.collector.impl;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggerContextVO;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.boncray.bean.constants.LogConstant;
import io.boncray.bean.mode.log.RpcLogItem;
import io.boncray.logback.collector.CollectionAble;
import io.boncray.logback.filter.LogbackFilter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/6 16:47
 */
public class RpcLogCollection implements CollectionAble<RpcLogItem> {

    private static final String SERVICE_NAME_KEY = "APP_NAME";
    /**
     * rpc 请求日志开始记录
     */
    private static final int REQUEST_START_ARG_SIZE = 4;
    /**
     * rpc 请求日志结束更新
     */
    private static final int REQUEST_END_ARG_SIZE = 2;


    @Override
    public boolean isNeedCollect(ILoggingEvent iLoggingEvent) {
        Map<String, String> mdcPropertyMap = iLoggingEvent.getMDCPropertyMap();
        boolean isNeed = false;
        if (LogbackFilter.class.getName().equals(iLoggingEvent.getLoggerName())) {
            isNeed = StrUtil.isNotBlank(mdcPropertyMap.get(LogConstant.PARENT_TRACK_ID))
                    && StrUtil.isNotBlank(mdcPropertyMap.get(LogConstant.CURRENT_TRACK_ID));
        }
        return isNeed ? isNeed(iLoggingEvent) : isNeed;
    }

    public boolean isNeed(ILoggingEvent iLoggingEvent) {
        return true;
    }

    @Override
    public RpcLogItem collectData(ILoggingEvent iLoggingEvent) {
        Map<String, String> mdcPropertyMap = iLoggingEvent.getMDCPropertyMap();
        LoggerContextVO contextVO = iLoggingEvent.getLoggerContextVO();
        Object[] logArgs = iLoggingEvent.getArgumentArray();

        RpcLogItem item = new RpcLogItem();
        item.setParentTrackId(Long.valueOf(mdcPropertyMap.get(LogConstant.PARENT_TRACK_ID)));
        item.setCurrentTrackId(Long.valueOf(mdcPropertyMap.get(LogConstant.CURRENT_TRACK_ID)));
        item.setServiceName(contextVO.getPropertyMap().get(SERVICE_NAME_KEY));
        this.parseLogArg(logArgs, item);
        item.setLogTime(DateUtil.toLocalDateTime(Instant.ofEpochMilli(iLoggingEvent.getTimeStamp())));
        return item;
    }

    /**
     * 解析日志里的参数
     */
    private void parseLogArg(Object[] logArgs, RpcLogItem item) {
        if (logArgs.length == REQUEST_START_ARG_SIZE) {
            Map<String, Object> requestParam = new HashMap<>(4);
            requestParam.put("body", logArgs[2]);
            requestParam.put("header", logArgs[3]);
            item.setMethod(logArgs[0].toString());
            item.setRequestPath(logArgs[1].toString());
            item.setRequestParam(requestParam);
        } else if (logArgs.length == REQUEST_END_ARG_SIZE) {
            item.setElapsedTime(Long.valueOf(logArgs[0].toString()));
            item.setResponseData(logArgs[1].toString());
        }
    }

    @Override
    public void transfer(RpcLogItem data) {
        System.err.println("rpc 传输数据：" + JSONUtil.toJsonStr(data));
    }
}
