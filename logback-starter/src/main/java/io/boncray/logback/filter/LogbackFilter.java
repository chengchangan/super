package io.boncray.logback.filter;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.boncray.bean.constants.LogConstant;
import io.boncray.bean.mode.response.Result;
import io.boncray.core.sequence.IdGenerator;
import io.boncray.logback.wapper.request.CustomHttpServletRequest;
import io.boncray.logback.wapper.request.CustomHttpServletRequestWrapper;
import io.boncray.logback.wapper.response.CustomHttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author cca
 * @version 1.0
 * @date 2021/8/4 15:00
 */
@Slf4j
@Component
public class LogbackFilter extends OncePerRequestFilter {


    @Autowired
    private IdGenerator idGenerator;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 使用包装创建自定义 request
        CustomHttpServletRequest customRequest = new CustomHttpServletRequest(new CustomHttpServletRequestWrapper(request));
        // 使用包装创建自定义 response
        CustomHttpServletResponse customResponse = new CustomHttpServletResponse(response);

        // 增加请求头，trackId
        this.putTrackId(customRequest);
        try {
            filterChain.doFilter(customRequest, customResponse);

            // 改写response，增加返回trackId
            this.overwriteResponse(customRequest, customResponse, response);
        } finally {
            MDC.remove(LogConstant.TRACK_ID);
        }
    }

    /**
     * 请求头、MDC ，
     * 增加 trackId 处理
     */
    private void putTrackId(CustomHttpServletRequest request) {
        String trackId = request.getHeader(LogConstant.TRACK_ID);
        if (StrUtil.isBlank(trackId)) {
            trackId = String.valueOf(idGenerator.next());
            request.putHeader(LogConstant.TRACK_ID, trackId);
        }
        MDC.put(LogConstant.TRACK_ID, trackId);
    }

    /**
     * 改写响应结果
     */
    private void overwriteResponse(CustomHttpServletRequest customRequest, CustomHttpServletResponse customResponse, HttpServletResponse response) throws IOException {
        String contentType = customResponse.getContentType();

        if (StringUtils.isNotBlank(contentType) && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
            String responseData = new String(customResponse.getResponseData());
            Result<?> result = JSONUtil.toBean(responseData, Result.class);
            result.setRequestId(Long.valueOf(customRequest.getHeader(LogConstant.TRACK_ID)));
            this.writeResponse(response, JSONUtil.toJsonStr(result));
        }
    }

    private void writeResponse(ServletResponse servletResponse, String responseData) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.reset();
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.setCharacterEncoding(ContentType.APPLICATION_JSON.getCharset().name());
        response.getWriter().write(responseData);
    }
}
