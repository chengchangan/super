package io.boncray.logback.transfer;

import io.boncray.logback.config.TransferChannel;
import io.boncray.logback.transfer.channel.DefaultChannel;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/7 00:43
 */
public class TransferFactory {


    // todo
    public static TransferAble getTransferor(TransferChannel channel) {
        switch (channel) {
            case ES:
                System.out.println("es");
            case MYSQL:
                System.out.println("mysql");
            default:
                return new DefaultChannel();
        }
    }

}
