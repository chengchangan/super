package com.cca.flow.process;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/14 14:08
 */
@Slf4j
@Component
public class ProcessLoader {

    @Autowired
    private ProcessRegistry processRegistry;


    @PostConstruct
    public void init() {
        // todo
        Process process = new Process();
        process.setBizCode("translate");
        process.setOperation("create");
        List<String> list = new ArrayList<>();
        list.add("initTranslate");
        list.add("productTranslate");
        list.add("addressTranslate");
        list.add("memberTranslate");
        list.add("finishedTranslate");
        process.setNodeList(list);
        processRegistry.registry(process);
    }

}
