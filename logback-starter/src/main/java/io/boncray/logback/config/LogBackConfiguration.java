package io.boncray.logback.config;

public class LogBackConfiguration {

    /**
     * 是否需要收集
     */
    private boolean collectEnabled;

    /**
     * 传输的方式
     */
    private TransferChannel transferChannel;





    enum TransferChannel {
        MYSQL, ES
    }
}
