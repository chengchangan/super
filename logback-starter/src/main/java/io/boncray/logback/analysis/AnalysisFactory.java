package io.boncray.logback.analysis;

import io.boncray.bean.mode.log.LogType;
import io.boncray.bean.utils.SpringContext;
import io.boncray.logback.analysis.analysor.NormalLogAnalyser;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/7 01:54
 */
public class AnalysisFactory {

    /**
     * spring上下文中的解析器
     */
    private static final Map<LogType, Analysis> analysisMap = new HashMap<>();

    public static Analysis getAnalyser(LogType logType) {
        loadFromSpringContext();
        Analysis analysis = analysisMap.get(logType);
        if (analysis == null) {
            analysis = new NormalLogAnalyser();
        }
        return analysis;
    }


    /**
     * 加载spring上下文中所有的解析器
     */
    private static void loadFromSpringContext() {
        ApplicationContext applicationContext = SpringContext.getApplicationContext();
        if (applicationContext != null) {
            Map<String, Analysis> beanMap = applicationContext.getBeansOfType(Analysis.class);
            beanMap.values().forEach(analysis -> analysisMap.put(analysis.supportType(), analysis));
        }
    }

}
