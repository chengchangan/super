package io.boncray.logback.wapper.response;


import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/1 14:33
 */
public class CustomHttpServletResponse extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream buffer;
    private final ServletOutputStream out;
    private final PrintWriter writer;

    public CustomHttpServletResponse(HttpServletResponse resp) {
        super(resp);
        // 真正存储数据的流
        buffer = new ByteArrayOutputStream();
        out = new WapperOutputStream(buffer);
        writer = new PrintWriter(new OutputStreamWriter(buffer));
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return out;
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (out != null) {
            out.flush();
        }
        if (writer != null) {
            writer.flush();
        }
    }

    @Override
    public void reset() {
        buffer.reset();
    }

    public byte[] getResponseData() throws IOException {
        flushBuffer();
        return buffer.toByteArray();
    }

    public String getContent() throws IOException {
        flushBuffer();
        return buffer.toString();
    }


    private static class WapperOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream bos = null;

        public WapperOutputStream(ByteArrayOutputStream stream) {
            bos = stream;
        }

        @Override
        public void write(int b) {
            bos.write(b);
        }

        @Override
        public void write(byte[] b) {
            bos.write(b, 0, b.length);
        }

        @Override
        public void write(byte[] b, int off, int len) {
            bos.write(b, off, len);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }
}
