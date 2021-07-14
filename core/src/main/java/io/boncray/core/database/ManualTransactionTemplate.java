package io.boncray.core.database;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 编程式事务模版.
 *
 * @author ginta
 * @author Shenzhen Greatonce Co Ltd
 * @version 2018/6/4
 */
public class ManualTransactionTemplate {

    private PlatformTransactionManager transactionManager;

    public ManualTransactionTemplate(
            PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * 在事务中执行代码.
     *
     * <p>事务传播类范围：Propagation.REQUIRED 事务类型：Isolation.DEFAULT
     *
     * @param runnable 执行方法
     */
    public void execute(TransactionRunnable runnable) {
        execute(Propagation.REQUIRED, Isolation.DEFAULT, -1, runnable);
    }

    /**
     * 在事务中执行代码.
     *
     * @param propagation 传播类型
     * @param runnable    执行方法
     */
    public void execute(Propagation propagation, TransactionRunnable runnable) {
        execute(propagation, Isolation.DEFAULT, -1, runnable);
    }

    /**
     * 在事务中执行代码.
     *
     * @param propagation 传播类型
     * @param isolation   事务级别
     * @param timeout     超时时间
     * @param runnable    执行方法
     */
    public void execute(Propagation propagation, Isolation isolation, int timeout,
                        TransactionRunnable runnable) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(propagation.value());
        definition.setIsolationLevel(isolation.value());
        definition.setTimeout(timeout);
        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            runnable.run();
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }


    /**
     * 在事务中执行代码.
     *
     * <p>事务传播类范围：Propagation.REQUIRED 事务类型：Isolation.DEFAULT
     *
     * @param callable 执行方法
     * @param <T>      返回类型
     * @return 返回值
     */
    public <T> T executeWithResult(TransactionCallable<T> callable) {
        return executeWithResult(Propagation.REQUIRED, Isolation.DEFAULT, -1, callable);
    }

    /**
     * 在事务中执行代码.
     *
     * @param propagation 传播类型
     * @param callable    执行方法
     * @param <T>         返回类型
     * @return 返回值
     */
    public <T> T executeWithResult(Propagation propagation, TransactionCallable<T> callable) {
        return executeWithResult(propagation, Isolation.DEFAULT, -1, callable);
    }

    /**
     * 在事务中执行代码.
     *
     * @param propagation 传播类型
     * @param isolation   事务级别
     * @param timeout     超时时间
     * @param callable    执行方法
     * @param <T>         返回类型
     * @return 返回值
     */
    public <T> T executeWithResult(Propagation propagation, Isolation isolation, int timeout,
                                   TransactionCallable<T> callable) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(propagation.value());
        definition.setIsolationLevel(isolation.value());
        definition.setTimeout(timeout);
        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            T t = callable.call();
            transactionManager.commit(status);
            return t;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
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
