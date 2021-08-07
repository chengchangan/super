package io.boncray.logback.transfer;

import io.boncray.logback.config.TransferChannel;
import io.boncray.logback.transfer.channel.DefaultChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/7 00:43
 */
public class TransferFactory {

    private static final Map<TransferChannel, Transferable> transferChannelMap = new ConcurrentHashMap<>(10);

    // todo
    public static Transferable getTransferor(TransferChannel channel) {
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
