package io.boncray.flow.process;

import com.google.common.base.Strings;
import io.boncray.flow.exception.FlowProcessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/13 20:13
 */
@Data
@EqualsAndHashCode(of = {"bizCode", "operation"})
@ToString
public class Process {

    /**
     * 业务场景
     */
    private String bizCode;

    /**
     * 业务场景下对应的业务操作
     */
    private String operation;

    /**
     * 业务场景下对应的业务操作的执行节点
     */
    private List<String> nodeList;


    public void check(boolean nodeListCheck) {
        if (Strings.isNullOrEmpty(bizCode)) {
            throw new FlowProcessException("process.biz.code.can.not.be.null.or.empty");
        }
        if (Strings.isNullOrEmpty(operation)) {
            throw new FlowProcessException("process.operation.can.not.be.null.or.empty");
        }
        if (nodeListCheck && CollectionUtils.isEmpty(nodeList)) {
            throw new FlowProcessException("process.node.list.can.not.be.null.or.empty");
        }
    }

}
