package io.boncray.logback.filter;

import cn.hutool.core.util.StrUtil;
import io.boncray.bean.constants.CommonConstant;
import io.boncray.bean.constants.LogConstant;
import io.boncray.bean.mode.log.TrackMetric;
import io.boncray.bean.mode.response.Result;
import io.boncray.common.utils.JacksonUtil;
import io.boncray.common.http.wapper.response.CustomHttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
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
 * 重写请求头
 *
 * @author cca
 * @version 1.0
 * @date 2021/8/5 13:42
 */
@Order(20)
@Slf4j
@Component
public class RewriteResponseFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
        // 改写response，增加返回trackId
        this.overwriteResponse(request, response);
    }


    /**
     * 改写响应结果
     */
    private void overwriteResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (response instanceof CustomHttpServletResponse) {
            CustomHttpServletResponse customResponse = (CustomHttpServletResponse) response;
            String contentType = customResponse.getContentType();
            if (StrUtil.isNotBlank(contentType) && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
                String responseData = new String(customResponse.getResponseData());
                Result<?> result = JacksonUtil.toObj(responseData, Result.class);
                TrackMetric trackMetric = JacksonUtil.toObj(request.getHeader(LogConstant.TRACK_METRIC), TrackMetric.class);
                result.setRequestId(trackMetric.getCurrentTrackId());
                // 写入请求的response
                this.writeResponse(customResponse.getResponse(), JacksonUtil.toJson(result));
                // 写入自定义的response
                this.writeResponse(customResponse, JacksonUtil.toJson(result));
            }
        }
    }

    private void writeResponse(ServletResponse servletResponse, String responseData) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.reset();
        response.setContentType(CommonConstant.APPLICATION_JSON);
        response.setCharacterEncoding(CommonConstant.UTF8);
        response.getWriter().write(responseData);
    }

}
