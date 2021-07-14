package io.boncray.core.monitor.pojo;

import lombok.Data;

/**
 * @author cca
 * @version 1.0
 * @date 2021/5/31 11:40
 */
@Data
public class MemoryInfo {

    /**
     * 已使用内存
     */
    private String usage;

    /**
     * 总内存
     */
    private String total;

    /**
     * 剩余库存数
     */
    private String remain;

    /**
     * 使用率
     */
    private String usageRate;

}
