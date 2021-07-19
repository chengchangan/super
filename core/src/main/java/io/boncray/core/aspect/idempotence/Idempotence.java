package io.boncray.core.aspect.idempotence;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/19 15:26
 */
@Target({ElementType.METHOD}) //目标是方法
@Retention(RetentionPolicy.RUNTIME) //注解会在class中存在，运行时可通过反射获取
@Documented //文档生成时，该注解将被包含在javadoc中，可去掉
public @interface Idempotence {


    /**
     * 幂等超时时间，第二个请求将抛出超时异常
     *
     * @return
     */
    long timeout() default -1L;

    /**
     * 超时单位，默认秒
     *
     * @return 单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;

}
