package com.cca.example.flow.node;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/13 19:59
 */
@Component
public class NodeRegistry implements ApplicationContextAware {

    private static final Map<String,Node> NODE_MAP = new HashMap<>();

    public Node getNode(String nodeCode){
        // 获取node
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 注册node




    }


}
