package io.boncray.core.monitor.pojo;

import lombok.Data;

/**
 * @author cca
 * @version 1.0
 * @date 2021/5/31 11:29
 */
@Data
public class CpuInfo {

    /**
     * cpu的名称
     */
    private String cpuName;

    /**
     * cpu核心数
     */
    private Integer coreNum;

    /**
     * cpu的频率
     */
    private String cpuFrequency;

    /**
     * 系统使用率
     */
    private String systemUsageRate;

    /**
     * 用户使用率
     */
    private String userUsageRate;

    /**
     * cpu空闲,系统有未完成的磁盘io
     */
    private String ioWaitRate;

    /**
     * 当前使用率
     */
    private String currentUsageRate;

}
