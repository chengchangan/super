package io.boncray.example.websocket.client;

import io.boncray.component.websocket.client.WebSocketClientManager;
import io.boncray.component.websocket.client.config.WebSocketConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/2 9:46
 */
@Configuration
public class WebsocketClientConfig {

    @Bean
    public WebSocketClientManager webSocketClientManager() {
        WebSocketConfig config = new WebSocketConfig();
        config.setConnectErrorRetryInterval(2);
//        config.setSslConfigMap(getSslConfigMap());
        return new WebSocketClientManager(config);
    }

    private Map<String, WebSocketConfig.SslConfig> getSslConfigMap() {
        Map<String, WebSocketConfig.SslConfig> map = new HashMap<>();
        WebSocketConfig.SslConfig sslConfig1 = getClientConfig1();

        map.put(sslConfig1.getHost(), sslConfig1);
        return map;
    }

    // 示例值，可从配置文件中取
    private WebSocketConfig.SslConfig getClientConfig1() {
        WebSocketConfig.SslConfig config = new WebSocketConfig.SslConfig();
        config.setIp("127.0.0.1");
        config.setPort("1998");
        return config;
    }
}
