package com.cca.example.websocket.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author cca
 * @version 1.0
 * @date 2021/5/18 16:18
 */
@Configuration
@EnableWebSocket
public class WebsocketServerConfig {
    @Bean
    public ServerEndpointExporter serverEndpoint() {
        return new ServerEndpointExporter();
    }
}