package io.boncray.flow.node;

import io.boncray.flow.exception.FlowProcessException;
import io.boncray.flow.exception.ProcessExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/13 19:59
 */
@Slf4j
@Component
public class NodeRegistry implements ApplicationContextAware {

    private static Map<String, Node> NODE_MAP;

    public Node getNode(String nodeCode) {
        return Optional
                .ofNullable(NODE_MAP.get(nodeCode))
                .orElseThrow(() -> new RuntimeException("process.node.code.not.exists:" + nodeCode));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        NODE_MAP = new HashMap<>(512);
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(StageNode.class);
        log.info("getBeansWithAnnotation(StageNode.class) result: {}", beanMap);
        if (!CollectionUtils.isEmpty(beanMap)) {
            beanMap.forEach((beanName, bean) -> {
                if (ClassUtils.isAssignableValue(Node.class, bean)) {
                    Node node = (Node) bean;
                    String nodeCode = node.getNodeCode();
                    if (NODE_MAP.containsKey(nodeCode)) {
                        throw new FlowProcessException(ProcessExceptionEnum.FLOW_NODE_100002.code(), ProcessExceptionEnum.FLOW_NODE_100002.msg() + nodeCode);
                    }
                    NODE_MAP.put(nodeCode, node);
                } else {
                    log.warn("ClassUtils.isAssignableValue(Node.class, node) = false, node:{}", bean);
                }
            });
        }
    }


}
