package io.boncray.logback.analysis.component;

import io.boncray.bean.mode.log.RpcLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogTempStore {

    private static final Map<String, RpcLog> logMap = new ConcurrentHashMap<>(10000);

    public static void put(String key, RpcLog log) {
        boolean containsKey = logMap.containsKey(key);
        if (!containsKey) {
            logMap.put(key, log);
        }
    }

    public static RpcLog getAndRemove(String key) {
        return logMap.remove(key);
    }

}
