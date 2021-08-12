package io.boncray.component.websocket.client.config;


import lombok.Data;

import java.util.Map;

/**
 * @author cca
 * @version 1.0
 * @date 2021/5/28 11:25
 */
@Data
public class WebSocketConfig {

    /**
     * client 打开连接超市时间（s）
     */
    private Integer connectTimeout = 5;
    /**
     * 超时后重连次数
     */
    private Integer timeoutRetryTimes = 3;
    /**
     * 目标服务器断开连接后，客户端多久进行重新连接（分钟）
     */
    private Integer connectErrorRetryInterval = 10;
    /**
     * key：ip + port
     * value：ssl配置
     */
    private Map<String, ClientConfig> clientConfigMap;

    @Data
    public static class ClientConfig {
        /**
         * websocket 服务端IP
         */
        private String ip;
        /**
         * websocket 服务端端口
         */
        private String port;
        /**
         * 服务地址
         */
        private String path;
        /**
         * 证书地址
         */
        private String certPath;
        /**
         * 证书密码
         */
        private String certPassWord;

        /**
         * @return ip : port
         */
        public String getHost() {
            return ip + ":" + port;
        }
    }

}
