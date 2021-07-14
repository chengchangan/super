package io.boncray.flow.node;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/13 19:49
 */
public interface Node<Context> {


    /**
     * 执行器方法，执行目标逻辑
     *
     * @param context 参数
     */
    void execute(Context context);


    /**
     * 节点编码
     *
     * @return 节点编码
     */
    default String getNodeCode() {
        StageNode stageNode = AnnotationUtils.findAnnotation(this.getClass(), StageNode.class);
        if (stageNode == null) {
            return this.getClass().getSimpleName();
        }
        if (StringUtils.isEmpty(stageNode.code())) {
            return this.getClass().getSimpleName();
        }
        return stageNode.code();
    }

}
