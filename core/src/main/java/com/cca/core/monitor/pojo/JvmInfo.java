package com.cca.core.monitor.pojo;

import lombok.Data;

/**
 * @author cca
 * @version 1.0
 * @date 2021/5/31 13:29
 */
@Data
public class JvmInfo {

    /**
     * jvm版本
     */
    private String jvmVersion;

    /**
     * jvm内存总量
     */
    private String total;

    /**
     * jvm已使用内存
     */
    private String usage;

    /**
     * jvm剩余可用内存
     */
    private String remain;

    /**
     * jvm内存使用率
     */
    private String usageRate;

    /**
     * jvm最大可申请内存
     */
    private String jvmMax;

}
