package io.boncray.logback.config;

import lombok.Data;

/**
 * 传输策略
 *
 * @author cca
 * @version 1.0
 * @date 2021/8/8 02:13
 */
@Data
public class TransferStrategy {

    /**
     * 每次直接传输
     */
    private Boolean always = false;



    /**
     * 多久传输一次
     */
    private int onceOfSecond = 1;

    /**
     * 每次最大多少条
     */
    private int batchMaxSize = 1000;


}
