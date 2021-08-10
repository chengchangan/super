package io.boncray.bean.mode.log;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/6 15:07
 */
@Data
public class RpcLog extends Log {

    /**
     * 主键
     */
    private Long id;

    /**
     * 父级　TrackId
     */
    private Long parentTrackId;

    /**
     * 当前 TrackId
     */
    private Long currentTrackId;

    /**
     * 日志所属服务
     */
    private String serviceName;

    /**
     * 日志级别
     */
    private String level;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 请求的路径
     */
    private String requestPath;

    /**
     * 请求参数（header和body）
     */
    private String requestParam;

    /**
     * 响应结果
     */
    private String responseData;

    /**
     * 耗时
     */
    private Long elapsedTime;

    /**
     * 日志产生时间
     */
    private LocalDateTime logTime;

    @Override
    public LogType logType() {
        return LogType.RPC_LOG;
    }
}
