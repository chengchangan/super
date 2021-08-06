package io.boncray.logback.logger.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import cn.hutool.core.collection.CollectionUtil;
import io.boncray.bean.mode.log.Log;
import io.boncray.logback.collection.CollectionAble;
import io.boncray.logback.logger.SpringContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/6 13:31
 */
public class CollectionLogFilter extends Filter<ILoggingEvent> {

    private Collection<CollectionAble> collectionList = new ArrayList<>();

    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        if (CollectionUtil.isEmpty(collectionList)) {
            Map<String, CollectionAble> beans = SpringContext.getApplicationContext().getBeansOfType(CollectionAble.class);
            if (CollectionUtil.isNotEmpty(beans)){
                collectionList.addAll(beans.values());
            }
        }
        if (CollectionUtil.isNotEmpty(collectionList)) {
            for (CollectionAble collection : collectionList) {
                if (collection.isNeedCollection(iLoggingEvent)) {
                    Log data = collection.getCollectionData(iLoggingEvent);
                    collection.transfer(data);
                }
            }
        }
        return FilterReply.NEUTRAL;
    }

}
