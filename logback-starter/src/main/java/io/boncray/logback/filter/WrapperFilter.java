package io.boncray.logback.filter;

import io.boncray.common.http.wapper.request.CustomHttpServletRequest;
import io.boncray.common.http.wapper.request.CustomHttpServletRequestWrapper;
import io.boncray.common.http.wapper.response.CustomHttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 包装 request response
 *
 * @author cca
 * @version 1.0
 * @date 2021/8/5 15:42
 */
@Order(-1)
@Slf4j
@Component
public class WrapperFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getContentType() != null
                && !"application/stream+json".equals(request.getContentType())
                && !"text/xml".equals(request.getContentType())
                && !"multipart/form-data".equals(request.getContentType())) {
            // 使用包装创建自定义 request
            CustomHttpServletRequest customRequest = new CustomHttpServletRequest(new CustomHttpServletRequestWrapper(request));
            // 使用包装创建自定义 response
            CustomHttpServletResponse customResponse = new CustomHttpServletResponse(response);
            filterChain.doFilter(customRequest, customResponse);
        } else {
            filterChain.doFilter(request, response);
        }

    }
}
