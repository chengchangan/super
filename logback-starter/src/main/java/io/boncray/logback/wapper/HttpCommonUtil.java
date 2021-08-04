package io.boncray.logback.wapper;

import io.boncray.bean.constants.CommonConstant;
import org.apache.http.entity.ContentType;

import javax.servlet.ServletRequest;
import java.io.IOException;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/4 16:10
 */
public class HttpCommonUtil {


    public static String getRequestPostStr(ServletRequest request) throws IOException {
        byte[] buffer = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = CommonConstant.UTF8;
        }
        return null != buffer ? new String(buffer, charEncoding) : null;
    }


    private static byte[] getRequestPostBytes(ServletRequest request) throws IOException {

        if (!ContentType.APPLICATION_JSON.getMimeType().equalsIgnoreCase(request.getContentType())) {
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
