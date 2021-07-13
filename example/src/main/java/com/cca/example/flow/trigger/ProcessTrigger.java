package com.cca.example.flow.trigger;

import com.cca.example.flow.node.Node;
import com.cca.example.flow.process.ProcessRegistry;
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

    public void fire(String bizCode, String operation, Object context) {
        List<Node> nodes = processRegistry.getProcessNodes(bizCode, operation);
        if (CollectionUtils.isEmpty(nodes)) {
            log.error("find nodes is null, bizCode = {}, operation = {}", bizCode, operation);
            throw new RuntimeException(MessageFormat.format("The trade node with bizCode [{0}] and operation [{1}] not exists.", bizCode, operation));
        }
        try {
            nodes.forEach(node -> {
                try {
//                    Profiler.record(node.getNodeCode());
                    node.execute(context);
                } finally {
//                    Profiler.release();
                }
            });
        } finally {
//            Profiler.releaseAll();
//            log.info("{}.{} Profiler dump = {}", operation, bizCode, Profiler.dump(true));
//            Profiler.reset();
        }
    }
}
