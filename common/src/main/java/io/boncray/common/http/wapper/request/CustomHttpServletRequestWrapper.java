package io.boncray.common.http.wapper.request;

import io.boncray.common.http.HttpCommonUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/4 16:09
 */
public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final String requestBody;
    private final StringBuffer url;


    public CustomHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.url = request.getRequestURL();
        try {
            requestBody = this.readBody(request);
        } catch (IOException e) {
            throw new RuntimeException("get request reader has IOException.", e);
        }
    }


    @Override
    public StringBuffer getRequestURL() {
        return this.url;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CustomHttpServletRequestWrapper.CustomServletInputStream(this.requestBody);
    }

    private String readBody(ServletRequest request) throws IOException {
        return HttpCommonUtil.getRequestPostStr(request);
    }

    private static class CustomServletInputStream extends ServletInputStream {
        private final ByteArrayInputStream buffer;

        CustomServletInputStream(String body) {
            body = body == null ? "" : body;
            this.buffer = new ByteArrayInputStream(body.getBytes());
        }

        @Override
        public int read() throws IOException {
            return this.buffer.read();
        }

        @Override
        public boolean isFinished() {
            return this.buffer.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new RuntimeException("未执行!");
        }
    }
}
