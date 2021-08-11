package io.boncray.common.http;


import javax.servlet.ServletRequest;
import java.io.IOException;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/4 16:10
 */
public class HttpCommonUtil {

    public static final String UTF8 = "UTF-8";
    public static final String APPLICATION_JSON = "application/json";


    public static String getRequestPostStr(ServletRequest request) throws IOException {
        byte[] buffer = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = UTF8;
        }
        return null != buffer ? new String(buffer, charEncoding) : null;
    }


    private static byte[] getRequestPostBytes(ServletRequest request) throws IOException {

        if (!APPLICATION_JSON.equalsIgnoreCase(request.getContentType())) {
            return null;
        } else {
            int contentLength = request.getContentLength();
            if (contentLength < 0) {
                return null;
            } else {
                byte[] buffer = new byte[contentLength];
                int readLen;

                for (int i = 0; i < contentLength; i += readLen) {
                    readLen = request.getInputStream().read(buffer, i, contentLength - i);
                    if (readLen == -1) {
                        break;
                    }
                }
                return buffer;
            }
        }
    }
}
