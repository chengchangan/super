package com.cca.flow.metric;

import com.google.common.base.Stopwatch;
import lombok.Data;

/**
 * 程序执行阶段
 *
 * @author cca
 * @version 1.0
 * @date 2021/7/13 19:49
 */
@Data
public class Stage {

    /**
     * 阶段描述
     */
    private String identifier;

    /**
     * 测量阶段耗时
     */
    private Stopwatch stopwatch;

    /**
     * 阶段是否结束
     */
    private boolean released;

    /**
     * 传入阶段描述，创建并开始测量流逝时间
     *
     * @param identifier 阶段描述
     */
    Stage(String identifier) {
        this.identifier = identifier;
        this.stopwatch = Stopwatch.createStarted();
    }

    /**
     * 结束阶段，停止流逝时间测量
     */
    public void release() {
        this.stopwatch.stop();
        this.released = true;
    }
}
