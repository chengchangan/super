package io.boncray.component.websocket.client;

import org.java_websocket.client.WebSocketClient;

/**
 * websocket收到消息后的处理
 *
 * @author cca
 * @version 1.0
 * @date 2021/5/28 17:32
 */
public interface MessageHandler {


    /**
     * 收到服务端的信息
     *
     * @param client  处理此信息的client
     * @param message 收到来自服务端的信息
     */
    void onMessage(WebSocketClient client, String message);

}
