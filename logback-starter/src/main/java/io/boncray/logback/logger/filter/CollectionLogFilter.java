package io.boncray.logback.logger.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import cn.hutool.core.collection.CollectionUtil;
import io.boncray.bean.mode.log.Log;
import io.boncray.bean.utils.SpringContext;
import io.boncray.logback.analysis.AnalysisFactory;
import io.boncray.logback.collector.Collectable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/6 13:31
 */
@Slf4j
public class CollectionLogFilter extends Filter<ILoggingEvent> {

    /**
     * spring 上下文中所有的收集器
     */
    private final Collection<Collectable> collectionList = new ArrayList<>();

    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        if (CollectionUtil.isEmpty(collectionList)) {
            loadFromSpringContext();
        }
        try {
            if (CollectionUtil.isNotEmpty(collectionList)) {
                for (Collectable<?> collection : collectionList) {
                    if (collection.isNeedCollect(iLoggingEvent)) {
                        Log logInfo = collection.collectData(iLoggingEvent);
                        try {
                            AnalysisFactory.getAnalyser(logInfo.logType()).analyse(logInfo);
                        } catch (Exception e) {
                            log.warn("日志分析失败:", e);
                        }

                    }
                }
            }
        } catch (Exception e) {
            log.warn("日志采集失败:", e);
        }
        return FilterReply.NEUTRAL;
    }


    /**
     * 从spring上下文中加载,收集器 和 配置信息
     */
    private void loadFromSpringContext() {
        ApplicationContext applicationContext = SpringContext.getApplicationContext();
        if (applicationContext != null) {
            collectionList.addAll(applicationContext.getBeansOfType(Collectable.class).values());
        }
    }

}
