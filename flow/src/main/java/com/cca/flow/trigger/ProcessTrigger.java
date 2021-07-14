package com.cca.flow.trigger;

import com.cca.flow.metric.Profiler;
import com.cca.flow.node.Node;
import com.cca.flow.process.ProcessRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/13 20:16
 */
@Slf4j
@Component
public class ProcessTrigger {

    @Autowired
    private ProcessRegistry processRegistry;

    @SuppressWarnings("unchecked")
    public void fire(String bizCode, String operation, Object context) {
        List<Node> nodes = processRegistry.getProcessNodes(bizCode, operation);
        if (CollectionUtils.isEmpty(nodes)) {
            log.error("find nodes is null, bizCode = {}, operation = {}", bizCode, operation);
            throw new RuntimeException(MessageFormat.format("The node with bizCode [{0}] and operation [{1}] not exists.", bizCode, operation));
        }
        try {
            nodes.forEach(node -> {
                try {
                    Profiler.record(node.getNodeCode());
                    node.execute(context);
                } finally {
                    Profiler.release();
                }
            });
        } finally {
            log.info("{}.{} Profiler dump = {}", bizCode, operation, Profiler.dump());
            Profiler.reset();
        }
    }
}
