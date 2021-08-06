package io.boncray.logback.analysis.component;

import io.boncray.bean.mode.log.RpcLogItem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogTempStore {

    private static final Map<String, RpcLogItem> logMap = new ConcurrentHashMap<>();

    public static void put(String key, RpcLogItem log) {
        boolean containsKey = logMap.containsKey(key);
        if (!containsKey) {
            logMap.put(key, log);
        }
    }

    public static RpcLogItem get(String key) {
        return logMap.get(key);
    }

}
