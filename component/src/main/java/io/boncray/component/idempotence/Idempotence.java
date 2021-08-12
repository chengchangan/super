package io.boncray.component.idempotence;

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
     * 幂等所在的组
     */
    String group() default "default";

    /**
     * 幂等key, 支持spel 表达式
     */
    String key();

    /**
     * 幂等请求阻塞超时时间，阻塞请求超时报错，默认不限制（一直阻塞）
     */
    long timeout() default -1L;

    /**
     * 超时单位，默认秒
     */
    TimeUnit unit() default TimeUnit.SECONDS;

}
