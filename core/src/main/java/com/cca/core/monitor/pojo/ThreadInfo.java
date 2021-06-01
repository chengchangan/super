package com.cca.core.monitor.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/5/31 13:29
 */
@Data
public class ThreadInfo {

    /**
     * 活动线程数
     */
    private Integer activeThread;

    /**
     * 线程列表
     */
    private List<SingleThread> threadList;

    @Data
    private static class SingleThread {

        /**
         * 线程Id
         */
        private String threadId;

        /**
         * 线程名称
         */
        private String threadName;

        /**
         * 线程状态
         */
        private String threadStatus;
    }
}
