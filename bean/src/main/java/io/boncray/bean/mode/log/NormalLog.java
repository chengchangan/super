package io.boncray.bean.mode.log;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/6 14:49
 */
@Data
public class NormalLog extends Log {

    /**
     * 主键
     */
    private Long id;

    /**
     * 日志类型
     */
    private String logType;

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
     * 日志名称（所属类）
     */
    private String loggerName;

    /**
     * 日志级别
     */
    private String level;

    /**
     * 日志信息
     */
    private String message;

    /**
     * 日志产生时间
     */
    private LocalDateTime logTime;


    @Override
    public LogType logType() {
        return LogType.NORMAL_LOG;
    }
}
