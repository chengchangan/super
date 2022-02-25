package io.boncray.flow.metric;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Joiner;
import io.boncray.common.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/14 9:55
 */
@Slf4j
public class Profiler {

    private static final ThreadLocal<LinkedList<Stage>> STACK_MAP = new ThreadLocal<>();


    /**
     * 记录一个新阶段
     */
    public static void record(String stage) {
        LinkedList<Stage> stages = STACK_MAP.get();
        if (stages == null) {
            stages = new LinkedList<>();
            stages.addLast(new Stage(stage));
            STACK_MAP.set(stages);
        } else {
            stages.addLast(new Stage(stage));
        }
    }

    /**
     * 释放掉最后一个的阶段
     */
    public static void release() {
        LinkedList<Stage> stages = STACK_MAP.get();
        if (CollectionUtil.isEmpty(stages)) {
            return;
        }
        Stage stage = stages.getLast();
        // 如果阶段已经被释放了，则不处理
        if (stage.isReleased()) {
            log.warn("阶段重复释放:{}", JacksonUtil.toJson(stage));
            return;
        }
        stage.release();
    }


    public static void releaseAll() {
        LinkedList<Stage> stages = STACK_MAP.get();
        if (CollectionUtil.isEmpty(stages)) {
            return;
        }
        stages.forEach(stage -> {
            if (!stage.isReleased()) {
                stage.release();
            }
        });
    }

    /**
     * 清理 ThreadLocal数据
     */
    public static void reset() {
        releaseAll();
        STACK_MAP.remove();
    }

    /**
     * 打印所有阶段日志信息
     *
     * @return 全链路日志信息
     */
    public static String dump() {
        List<Stage> stages = STACK_MAP.get();
        if (CollectionUtil.isEmpty(stages)) {
            return "{UNRECORDED}";
        }
        Map<String, String> stageMap = new LinkedHashMap<>(stages.size());
        for (int i = 1; i <= stages.size(); i++) {
            Stage stage = stages.get(i - 1);
            String key = i + " - " + stage.getIdentifier();
            String value = stage.isReleased() ? String.valueOf(stage.getStopwatch().elapsed(TimeUnit.MILLISECONDS)) : "unreleased";
            stageMap.put(key, value);
        }
        return "{" + Joiner.on(" | ").withKeyValueSeparator(":").join(stageMap) + "}";
    }


}
