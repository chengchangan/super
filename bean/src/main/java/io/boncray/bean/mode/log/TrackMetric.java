package io.boncray.bean.mode.log;

import lombok.Data;

/**
 * 调用追踪记录
 *
 * @author cca
 * @version 1.0
 * @date 2021/8/5 9:17
 */
@Data
public class TrackMetric {

    /**
     * 当前 TrackId
     */
    private Long currentTrackId;

    /**
     * 父级　TrackId
     */
    private Long parentTrackId;

}
