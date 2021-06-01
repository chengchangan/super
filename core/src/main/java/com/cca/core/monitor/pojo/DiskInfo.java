package com.cca.core.monitor.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/5/31 13:29
 */
@Data
public class DiskInfo {

    /**
     * 名称
     */
    private String name;

    /**
     * 模式
     */
    private String mode;
    /**
     * 序列号
     */
    private String serial;

    /**
     * 总大小
     */
    private String total;

    /**
     * 读的次数
     */
    private long reads;
    /**
     * 读到的大小
     */
    private String readCount;

    /**
     * 写的次数
     */
    private long writes;

    /**
     * 写入的大小
     */
    private String writeCount;

    /**
     * 传输的时间，包括读写（毫秒）
     */
    private long transferTime;

    /**
     * 分区信息
     */
    private List<Partition> partitions;

    @Data
    public static class Partition {

        /**
         * 名称
         */
        private String name;

        /**
         * 类型
         */
        private String type;

        /**
         * 大小
         */
        private String size;

        /**
         * 挂载点
         */
        private String mountPoint;

        /**
         * 已用空间大小
         */
        private String usedSpace;

        /**
         * 可用空间大小
         */
        private String canUseSpace;


    }
}
