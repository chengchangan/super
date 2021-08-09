package io.boncray.logback.transfer.channel;

import io.boncray.logback.transfer.Transferable;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/7 00:43
 */
public class DefaultChannel implements Transferable {


    @Override
    public void transfer(Object data) {
        // 如果不配传输方式,默认不传输

    }

}
