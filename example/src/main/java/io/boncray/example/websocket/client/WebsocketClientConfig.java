package io.boncray.example.websocket.client;

import io.boncray.core.websocket.client.WebSocketClientManager;
import io.boncray.core.websocket.client.config.WebSocketConfig;
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
        config.setClientConfigMap(getClientConfigMap());
        return new WebSocketClientManager(config);
    }

    private Map<String, WebSocketConfig.ClientConfig> getClientConfigMap() {
        Map<String, WebSocketConfig.ClientConfig> map = new HashMap<>();
        WebSocketConfig.ClientConfig clientConfig1 = getClientConfig1();

        map.put(clientConfig1.getHost(), clientConfig1);
        return map;
    }

    // 示例值，可从配置文件中取
    private WebSocketConfig.ClientConfig getClientConfig1() {
        WebSocketConfig.ClientConfig config = new WebSocketConfig.ClientConfig();
        config.setIp("127.0.0.1");
        config.setPort("8080");
        return config;
    }
}
