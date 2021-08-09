package io.boncray.logback.collector.impl;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;
import ch.qos.logback.classic.spi.ThrowableProxy;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.boncray.bean.constants.LogConstant;
import io.boncray.bean.mode.log.RpcLog;
import io.boncray.core.util.JacksonUtil;
import io.boncray.logback.collector.Collectable;
import io.boncray.logback.filter.LogbackFilter;
import io.boncray.logback.wapper.request.CustomHttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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


    private boolean normalCall(ILoggingEvent iLoggingEvent) {
        Map<String, String> mdcPropertyMap = iLoggingEvent.getMDCPropertyMap();
        if (LogbackFilter.class.getName().equals(iLoggingEvent.getLoggerName())) {
            return StrUtil.isNotBlank(mdcPropertyMap.get(LogConstant.PARENT_TRACK_ID))
                    && StrUtil.isNotBlank(mdcPropertyMap.get(LogConstant.CURRENT_TRACK_ID));
        }
        return false;
    }

    private boolean exceptionCall(ILoggingEvent iLoggingEvent) {

        CustomHttpServletRequest request = (CustomHttpServletRequest)((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String header = request.getHeader(LogConstant.TRACK_METRIC);


        ThrowableProxy throwableProxy = (ThrowableProxy)iLoggingEvent.getThrowableProxy();
        if (throwableProxy.getThrowable() != null){
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
        item.setServiceName(contextVO.getPropertyMap().get(SERVICE_NAME_KEY));
        this.parseLogArg(logArgs, item);
        item.setLogTime(DateUtil.toLocalDateTime(Instant.ofEpochMilli(iLoggingEvent.getTimeStamp())));
        return item;
    }

    /**
     * 解析日志里的参数
     */
    private void parseLogArg(Object[] logArgs, RpcLog item) {
        if (logArgs.length == REQUEST_START_ARG_SIZE) {
            Map<String, Object> requestParam = new HashMap<>(4);
            requestParam.put("body", logArgs[2]);
            requestParam.put("header", logArgs[3]);
            item.setMethod(logArgs[0].toString());
            item.setRequestPath(logArgs[1].toString());
            item.setRequestParam(JacksonUtil.toJson(requestParam));
        } else if (logArgs.length == REQUEST_END_ARG_SIZE) {
            item.setElapsedTime(Long.valueOf(logArgs[0].toString()));
            item.setResponseData(logArgs[1].toString());
        }
    }

}
