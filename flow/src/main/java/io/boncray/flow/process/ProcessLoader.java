package io.boncray.flow.process;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/14 14:08
 */
@Slf4j
@Component
public class ProcessLoader {

    /**
     * default node paths
     */
    private static final String DEFAULT_PATHS = "PROCESS-FLOW/process_flow_*.json";

    /**
     * <p>
     * custom node paths
     */
    @Value("${process.flow.custom.location:PROCESS-FLOW/custom_process_flow_*.json}")
    private String customPath;
    @Value("${process.flow.overrideDefaultNodes:true}")
    private Boolean overrideDefaultNodes;

    private final ApplicationContext applicationContext;
    private final ProcessRegistry processRegistry;

    public ProcessLoader(ApplicationContext applicationContext, ProcessRegistry processRegistry) {
        this.applicationContext = applicationContext;
        this.processRegistry = processRegistry;
    }

    @PostConstruct
    public void init() throws IOException {
        try {
            log.info("[START_LOAD_PROCESS_FLOWS] defaultPath: {}, customPath: {}, overrideDefaultNodes: {}",
                    DEFAULT_PATHS, customPath, overrideDefaultNodes);

            List<Process> defaultProcess = loadProcessConfig(DEFAULT_PATHS);
            List<Process> customProcess = loadProcessConfig(customPath);

            // 校验自定义的与默认的是否冲突
            if (overrideDefaultNodes) {
                if (!Collections.disjoint(defaultProcess, customProcess)) {
                    throw new RuntimeException("trade.duplicate.process.config");
                }
            }
            // default merge custom put all to registry
            Set<Process> processSet = Sets.newHashSet(customProcess);
            processSet.addAll(defaultProcess);
            processRegistry.registryAll(processSet);
        } catch (Throwable e) {
            log.error("load process flow error: {}", Throwables.getStackTraceAsString(e));
            throw e;
        }
    }

    /**
     * load process
     *
     * @param resourcePath resource path
     * @return list of process
     * @throws IOException
     */
    protected List<Process> loadProcessConfig(String resourcePath) throws IOException {
        List<Process> processes = Lists.newArrayList();
        Resource[] resources = applicationContext.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resourcePath);
        for (Resource resource : resources) {
            String json = CharStreams.toString(new InputStreamReader(resource.getInputStream(), Charsets.UTF_8));
            processes.add(JSON.parseObject(json, Process.class));
        }
        return processes;
    }

}
