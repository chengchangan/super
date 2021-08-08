package io.boncray.logback.transfer.station;

import cn.hutool.core.collection.CollectionUtil;
import io.boncray.bean.mode.log.Log;
import io.boncray.logback.config.LogBackConfiguration;
import io.boncray.logback.config.TransferStrategy;
import io.boncray.logback.transfer.station.execute.TransferExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/8 02:23
 */
public class LogCacheStore {

    private static final Queue<Log> cacheLogQueue = new ConcurrentLinkedQueue<>();

    /**
     * 传输策略
     */
    private final TransferStrategy transferStrategy;

    /**
     * 日志传输器
     */
    private final TransferExecutor transferExecutor;

    /**
     * 定时传输线程池
     */
    private static final ScheduledExecutorService TIMING_TRANSFER_EXECUTOR = Executors.newScheduledThreadPool(1,
            x -> new Thread(x, LogCacheStore.class.getName()));


    public LogCacheStore(LogBackConfiguration configuration, TransferExecutor transferExecutor) {
        this.transferStrategy = configuration.getTransferStrategy();
        this.transferExecutor = transferExecutor;
        // 定时传输日志
        TIMING_TRANSFER_EXECUTOR.scheduleWithFixedDelay(new TransferRunnable(), 10, Math.max(transferStrategy.getOnceOfSecond(), 10), TimeUnit.SECONDS);
    }


    /**
     * 缓存日志信息
     */
    public void putLog(Log log) {
        cacheLogQueue.offer(log);
    }


    /**
     * 定时任务传输
     */
    public class TransferRunnable implements Runnable {

        @Override
        public void run() {
            // 批量传输数据的size
            int batchTransferSize = cacheLogQueue.size();
            if (batchTransferSize > transferStrategy.getBatchMaxSize()) {
                batchTransferSize = transferStrategy.getBatchMaxSize();
            }
            if (batchTransferSize <= 0) {
                return;
            }

            List<Log> batchList = new ArrayList<>(batchTransferSize);
            while (batchTransferSize-- > 0) {
                batchList.add(cacheLogQueue.poll());
            }

            if (CollectionUtil.isNotEmpty(batchList)) {
                transferExecutor.doTransfer(batchList);
            }
        }

    }

}
