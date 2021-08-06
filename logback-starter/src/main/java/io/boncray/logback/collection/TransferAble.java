package io.boncray.logback.collection;

/**
 * 数据传输接口
 *
 * @author cca
 * @version 1.0
 * @date 2021/8/6 16:39
 */
public interface TransferAble<T> {

    /**
     * 数据传输
     *
     * @param data 传输的数据
     */
    void transfer(T data);

}
