package io.boncray.core.monitor.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/5/31 19:57
 */
@Data
public class ProcessInfo {

    /**
     * 进程的总数
     */
    private int processCount;

    /**
     * 线程总数
     */
    private int threadCount;
    /**
     * 内存占用前10的进程信息
     */
    private List<SingleProcessInfo> memoryTop10ProcessList;
    /**
     * cpu占用前10的进程信息
     */
    private List<SingleProcessInfo> cpuTop10ProcessList;


    @Data
    public static class SingleProcessInfo {

        /**
         * 进程pid
         */
        private int pid;

        /**
         * 进程名称
         */
        private String processName;

        /**
         * cpu的使用率
         */
        private String cpuUsageRate;

        /**
         * 内存使用率
         */
        private String memUsageRate;

        /**
         * Virtual Memory Size，虚拟内存大小，
         */
        private String vsz;

        /**
         *  Resident Set Size，实际常驻内存集合大小
         */
        private String rss;
    }

}
