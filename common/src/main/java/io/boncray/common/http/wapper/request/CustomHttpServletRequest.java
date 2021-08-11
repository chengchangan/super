package io.boncray.common.http.wapper.request;

import org.apache.commons.collections.map.CaseInsensitiveMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/4 16:32
 */
public class CustomHttpServletRequest extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders = new CaseInsensitiveMap();

    public CustomHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.init(request);
    }

    private void init(HttpServletRequest request) {
        Enumeration<String> headerNames = this.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            customHeaders.put(headerName, request.getHeader(headerName));
        }
    }

    public void putHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = this.customHeaders.get(name);
        return headerValue != null ? headerValue : ((HttpServletRequest) this.getRequest()).getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {

        Set<String> set = new HashSet<>(this.customHeaders.keySet());
        Enumeration<String> headerNames = ((HttpServletRequest) this.getRequest()).getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            set.add(headerName);
        }
        return Collections.enumeration(set);
    }
}
