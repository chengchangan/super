package com.cca.example.websocket.client;

import com.cca.core.websocket.client.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.springframework.stereotype.Component;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/2 9:58
 */
@Component
@Slf4j
public class TestMessageHandler implements MessageHandler {


    @Override
    public void onMessage(WebSocketClient client, String message) {
        log.info("收到websocket server的信息：{}", message);
    }


}
