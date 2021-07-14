package io.boncray.core.aspect.retry;

import java.lang.annotation.*;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/28 9:18
 */
@Target({ ElementType.METHOD}) //目标是方法
@Retention(RetentionPolicy.RUNTIME) //注解会在class中存在，运行时可通过反射获取
@Documented //文档生成时，该注解将被包含在javadoc中，可去掉
public @interface Retry {

    /**
     * 重试次数
     */
    int times() default 3;

    /**
     * 每次重试间隔时间（秒）
     */
    int intervalSecond() default 3;

    /**
     * 针对指定异常才重试
     */
    Class<? extends Throwable>[] retryFor() default {};

}
