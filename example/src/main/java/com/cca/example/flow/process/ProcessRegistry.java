package com.cca.example.flow.process;

import cn.hutool.core.collection.CollectionUtil;
import com.cca.example.flow.node.Node;
import com.cca.example.flow.node.NodeRegistry;
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


    public void add(Process process) {
        // todo check
        process.check(true);
        if (PROCESS_MAP.containsKey(process)) {
            throw new RuntimeException("流程已存在");
        }
        List<Node> collect = process.getNodeList().stream().map(nodeRegistry::getNode).collect(Collectors.toList());
        PROCESS_MAP.put(process, collect);
    }

    public void addAll(Collection<Process> collection) {
        if (CollectionUtil.isNotEmpty(collection)) {
            collection.forEach(this::add);
        }
    }

    public List<Node> getProcessNodes(String bizCode, String operationCode) {
        Process process = new Process();
        process.setBizCode(bizCode);
        process.setOperationCode(operationCode);
        process.check(false);
        return PROCESS_MAP.get(process);
    }

}
