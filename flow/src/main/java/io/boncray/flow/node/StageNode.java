package io.boncray.flow.node;

import java.lang.annotation.*;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/13 20:11
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface StageNode {

    /**
     * @return 节点code
     */
    String code() default "";

}
