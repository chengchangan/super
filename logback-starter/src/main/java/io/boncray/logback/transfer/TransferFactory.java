package io.boncray.logback.transfer;

import io.boncray.bean.utils.SpringContext;
import io.boncray.logback.config.TransferChannel;
import io.boncray.logback.transfer.channel.DefaultChannel;
import io.boncray.logback.transfer.channel.es.EsTransfer;
import io.boncray.logback.transfer.channel.mysql.MysqlTransfer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/7 00:43
 */
public class TransferFactory {

    private static final Map<TransferChannel, Transferable> transferChannelMap = new ConcurrentHashMap<>(10);

    public static Transferable getTransferor(TransferChannel channel) {
        switch (channel) {
            case ES:
                return transferChannelMap.computeIfAbsent(channel, (x) -> SpringContext.getBean(EsTransfer.class));
            case MYSQL:
                return transferChannelMap.computeIfAbsent(channel, (x) -> SpringContext.getBean(MysqlTransfer.class));
            default:
                return new DefaultChannel();
        }
    }

}
