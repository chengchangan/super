package io.boncray.bean.mode.log;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/6 15:07
 */
@Data
public class RpcLogItem extends Log {

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
    private Map<String, Object> requestParam;

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
}
