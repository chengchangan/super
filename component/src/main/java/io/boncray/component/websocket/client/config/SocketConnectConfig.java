package io.boncray.component.websocket.client.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/2 14:39
 */
@Data
@AllArgsConstructor
public class SocketConnectConfig {

    /**
     * websocket 服务端IP
     */
    private String ip;

    /**
     * 端口
     */
    private String port;

    /**
     * websocket 服务端 路径
     */
    private String path;

    /**
     * 路径参数
     */
    private List<String> params;


    /**
     * @return ip + port + path + param
     */
    public String getFullPath() {
        StringBuilder fullPath = new StringBuilder();
        fullPath.append(ip).append(":").append(port);

        if (StrUtil.isNotBlank(path)) {
            fullPath.append(path);
        }
        if (CollectionUtil.isNotEmpty(params)) {
            params.forEach(x -> fullPath.append("/").append(x));
        }
        return fullPath.toString();
    }

    /**
     * ip + port
     */
    public String getHost() {
        return ip + ":" + port;
    }
}
