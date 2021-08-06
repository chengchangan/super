package io.boncray.flow.trigger;

import cn.hutool.json.JSONUtil;
import io.boncray.core.database.ManualTransaction;
import io.boncray.flow.exception.FlowProcessException;
import io.boncray.flow.exception.ProcessExceptionEnum;
import io.boncray.flow.metric.Profiler;
import io.boncray.flow.node.BaseContext;
import io.boncray.flow.node.Node;
import io.boncray.flow.process.ProcessRegistry;
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
    @Autowired
    private ManualTransaction manualTransaction;

    @SuppressWarnings("unchecked")
    public void fire(BaseContext context) {
        context.check();
        String bizCode = context.getBizCode();
        String operation = context.getOperation();

        List<Node> nodes = processRegistry.getProcessNodes(bizCode, operation);
        if (CollectionUtils.isEmpty(nodes)) {
            log.error("find nodes is null, bizCode = {}, operation = {}", bizCode, operation);
            throw new FlowProcessException(ProcessExceptionEnum.FLOW_TRIGGER_120001.code(),
                    MessageFormat.format("The node with bizCode [{0}] and operation [{1}] not exists.", bizCode, operation));
        }
        try {
            nodes.forEach(node -> {
                // 当前节点执行
                try {
                    // 记录节点操作信息
                    Profiler.record(node.getNodeCode());
                    // 是否按照事务的方式执行
                    if (node.supportTransaction()) {
                        manualTransaction.execute(() -> node.execute(context));
                    } else {
                        node.execute(context);
                    }
                } catch (Exception e) {
                    log.error("node:{},执行失败,原因:{}.参数:{}", node.getNodeCode(), e.getMessage(), JSONUtil.toJsonStr(context));
                    throw new FlowProcessException(ProcessExceptionEnum.FLOW_TRIGGER_120002, e);
                } finally {
                    // 结束当前节点计时
                    Profiler.release();
                }

            });
        } finally {
            // 记录日志
            log.info("{}.{} Profiler dump = {}", bizCode, operation, Profiler.dump());
            // 清空日志信息
            Profiler.reset();
        }
    }
}
