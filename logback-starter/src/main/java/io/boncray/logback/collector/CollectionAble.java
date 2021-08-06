package io.boncray.logback.collector;

import ch.qos.logback.classic.spi.ILoggingEvent;
import io.boncray.bean.mode.log.Log;
import io.boncray.logback.transfer.TransferAble;

/**
 * 数据收集接口
 *
 * @author cca
 * @version 1.0
 * @date 2021/8/6 16:30
 */
public interface CollectionAble<T extends Log> extends TransferAble<T> {


    /**
     * 是否需要收集
     *
     * @param iLoggingEvent 日志事件
     * @return 是否需要收集
     */
    boolean isNeedCollect(ILoggingEvent iLoggingEvent);


    /**
     * 获取收集的数据
     *
     * @param iLoggingEvent 日志事件
     * @return 返回收集到的数据
     */
    T collectData(ILoggingEvent iLoggingEvent);


}
