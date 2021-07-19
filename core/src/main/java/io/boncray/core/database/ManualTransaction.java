package io.boncray.core.database;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cca
 * @version 1.0
 * @date 2020/12/10 15:38
 */
@Component
public class ManualTransaction {

    @Transactional(rollbackFor = Exception.class)
    public void execute(TransactionRunnable transactionRunnable) {
        transactionRunnable.run();
    }

    @Transactional(rollbackFor = Exception.class)
    public <T> T executeWithResult(TransactionCallable<T> transactionCallable) {
        return transactionCallable.call();
    }

    @FunctionalInterface
    public interface TransactionRunnable {
        void run();
    }

    @FunctionalInterface
    public interface TransactionCallable<V> {
        V call();
    }

}
