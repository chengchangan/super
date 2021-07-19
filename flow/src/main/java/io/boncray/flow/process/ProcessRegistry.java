package io.boncray.flow.process;

import cn.hutool.core.collection.CollectionUtil;
import io.boncray.flow.exception.FlowProcessException;
import io.boncray.flow.node.Node;
import io.boncray.flow.node.NodeRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/13 20:16
 */
@Component
public class ProcessRegistry {

    private static final Map<Process, List<Node>> PROCESS_MAP = new HashMap<>();

    @Autowired
    private NodeRegistry nodeRegistry;


    public void registry(Process process) {
        process.check(true);
        if (PROCESS_MAP.containsKey(process)) {
            throw new FlowProcessException("流程已存在");
        }
        List<Node> collect = process.getNodeList().stream().map(nodeRegistry::getNode).collect(Collectors.toList());
        PROCESS_MAP.put(process, collect);
    }

    public void registryAll(Collection<Process> collection) {
        if (CollectionUtil.isNotEmpty(collection)) {
            collection.forEach(this::registry);
        }
    }

    public List<Node> getProcessNodes(String bizCode, String operation) {
        Process process = new Process();
        process.setBizCode(bizCode);
        process.setOperation(operation);
        process.check(false);
        return PROCESS_MAP.get(process);
    }

}
