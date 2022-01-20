package io.boncray.logback.filter;

import com.google.common.collect.Lists;
import io.boncray.common.http.wapper.request.CustomHttpServletRequest;
import io.boncray.common.http.wapper.request.CustomHttpServletRequestWrapper;
import io.boncray.common.http.wapper.response.CustomHttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    private static final List<String> WRAPPER_CONTENT_TYPE_LIST = Lists.newArrayList(MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (WRAPPER_CONTENT_TYPE_LIST.contains(request.getContentType())) {
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
