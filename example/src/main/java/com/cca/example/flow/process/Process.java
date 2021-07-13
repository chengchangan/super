package com.cca.example.flow.process;

import com.google.common.base.Strings;
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
@EqualsAndHashCode(of = {"bizCode", "operationCode"})
@ToString
public class Process {

    /**
     * 业务场景
     */
    private String bizCode;

    /**
     * 业务场景下对应的业务操作
     */
    private String operationCode;

    /**
     * 业务场景下对应的业务操作的执行节点
     */
    private List<String> nodeList;


    public void check(boolean nodeListCheck) {
        if (Strings.isNullOrEmpty(bizCode)) {
            throw new RuntimeException("process.biz.code.can.not.be.null.or.empty");
        }
        if (Strings.isNullOrEmpty(operationCode)) {
            throw new RuntimeException("process.operation.can.not.be.null.or.empty");
        }
        if (nodeListCheck && CollectionUtils.isEmpty(nodeList)) {
            throw new RuntimeException("process.node.list.can.not.be.null.or.empty");
        }
    }

}
