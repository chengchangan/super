package io.boncray.logback.filter;


import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.boncray.bean.constants.LogConstant;
import io.boncray.bean.mode.log.LogType;
import io.boncray.bean.mode.log.TrackMetric;
import io.boncray.common.http.HttpCommonUtil;
import io.boncray.common.http.wapper.request.CustomHttpServletRequest;
import io.boncray.common.http.wapper.response.CustomHttpServletResponse;
import io.boncray.common.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import io.boncray.core.sequence.IdGenerator;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * 请求头 trackMetric 及 日志打印
 *
 * @author cca
 * @version 1.0
 * @date 2021/8/4 15:00
 */
@Order(1)
@Slf4j
@Component
public class LogbackFilter extends OncePerRequestFilter {


    @Resource
    private IdGenerator normalIdGenerator;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        CustomHttpServletRequest customRequest = (CustomHttpServletRequest) request;
        CustomHttpServletResponse customResponse = (CustomHttpServletResponse) response;

        // 增加请求头,处理trackMetric 信息
        this.parseTrack(customRequest);

        // 设置MDC
        this.parseMDC(customRequest);

        // 接口访问日志记录
        this.startWriteLog(customRequest);
        try {
            filterChain.doFilter(customRequest, customResponse);
            // 响应结果日志记录（更新）
            this.endWriteLog(customResponse, start);
        } finally {
            this.cleanMDC();
        }
    }

    /**
     * 请求日志记录
     */
    private void startWriteLog(CustomHttpServletRequest customRequest) throws IOException {
        String body = HttpCommonUtil.getRequestPostStr(customRequest);

        Map<String, String> headerMap = new HashMap<>();
        Enumeration<String> headerNames = customRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, customRequest.getHeader(headerName));
        }
        log.info("request logType:{},{},method:{},path:{},body:{},header:{}", LogType.RPC_LOG, "start",
                customRequest.getMethod(), customRequest.getRequestURI(), body, JacksonUtil.toJson(headerMap));
    }

    private void endWriteLog(CustomHttpServletResponse customResponse, long start) throws IOException {
        String responseData = new String(customResponse.getResponseData());
        JSONObject response = JSONUtil.parseObj(responseData);
        if (BooleanUtil.isTrue(response.getBool("success", false))) {
            log.info("response logType:{},{},elapsedTime:{},data:{}", LogType.RPC_LOG, "end",
                    System.currentTimeMillis() - start, responseData);
        } else {
            log.error("response logType:{},{},elapsedTime:{},data:{}", LogType.RPC_LOG, "end",
                    System.currentTimeMillis() - start, responseData);
        }
    }


    private void parseTrack(CustomHttpServletRequest request) {
//        parseTrackMetric(request);
        parseTrackMetricTree(request);
    }

    /**
     * 请求头、MDC ,
     * 增加 trackId 处理
     */
    private void parseTrackMetricTree(CustomHttpServletRequest request) {
        String parentMetricStr = request.getHeader(LogConstant.TRACK_METRIC);

        TrackMetric currentMetric = new TrackMetric();
        currentMetric.setCurrentTrackId(normalIdGenerator.next());
        // 父级调用
        if (StrUtil.isNotBlank(parentMetricStr)) {
            TrackMetric parentMetric = JSONUtil.toBean(parentMetricStr, TrackMetric.class);
            currentMetric.setParentTrackId(parentMetric.getCurrentTrackId());
        }
        request.putHeader(LogConstant.TRACK_METRIC, JacksonUtil.toJson(currentMetric));
    }


    private void parseTrackMetric(CustomHttpServletRequest request) {
        String parentMetricStr = request.getHeader(LogConstant.TRACK_METRIC);
        if (StrUtil.isBlank(parentMetricStr)) {
            TrackMetric currentMetric = new TrackMetric();
            currentMetric.setCurrentTrackId(normalIdGenerator.next());
            request.putHeader(LogConstant.TRACK_METRIC, JacksonUtil.toJson(currentMetric));
        }
    }

    private void parseMDC(CustomHttpServletRequest request) {
        TrackMetric currentMetric = JSONUtil.toBean(request.getHeader(LogConstant.TRACK_METRIC), TrackMetric.class);
        MDC.put(LogConstant.PARENT_TRACK_ID, String.valueOf(Optional.ofNullable(currentMetric.getParentTrackId()).orElse(0L)));
        MDC.put(LogConstant.CURRENT_TRACK_ID, String.valueOf(currentMetric.getCurrentTrackId()));
    }

    private void cleanMDC() {
        MDC.remove(LogConstant.PARENT_TRACK_ID);
        MDC.remove(LogConstant.CURRENT_TRACK_ID);
    }


}
