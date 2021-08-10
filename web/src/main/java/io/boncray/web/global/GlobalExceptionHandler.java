package io.boncray.web.global;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.boncray.bean.constants.LogConstant;
import io.boncray.bean.exception.BizException;
import io.boncray.bean.mode.log.TrackMetric;
import io.boncray.bean.mode.response.MessageState;
import io.boncray.bean.mode.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/20 14:42
 */
@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<Object> webExceptionHandler(Exception e, HttpServletRequest request) {
        if (request != null) {
            log.error("异常URL:{}", request.getRequestURI());
        }
        log.error("系统异常,原因:{}", e.getMessage(), e);
        Result<Object> result = Result.failure(MessageState.UNKNOWN_ERROR.getCode(), MessageState.UNKNOWN_ERROR.getMsg() + "," + e.getMessage());

        String trackMetricStr = request.getHeader(LogConstant.TRACK_METRIC);
        if (StrUtil.isNotBlank(trackMetricStr)) {
            TrackMetric trackMetric = JSONUtil.toBean(trackMetricStr, TrackMetric.class);
            result.setRequestId(trackMetric.getCurrentTrackId());
        }
        return result;
    }

    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<Object> omsExceptionHandler(BizException e, HttpServletRequest request) {
        log.error("业务异常,原因:{}", e.getMessage(), e);
        Result<Object> result = Result.failure(e.getCode(), e.getMessage());
        String trackMetricStr = request.getHeader(LogConstant.TRACK_METRIC);
        if (StrUtil.isNotBlank(trackMetricStr)) {
            TrackMetric trackMetric = JSONUtil.toBean(trackMetricStr, TrackMetric.class);
            result.setRequestId(trackMetric.getCurrentTrackId());
        }
        return result;
    }

}
