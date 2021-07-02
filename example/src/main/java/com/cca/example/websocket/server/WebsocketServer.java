package com.cca.example.websocket.server;

/**
 * @author cca
 * @version 1.0
 * @date 2021/5/18 15:07
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint(value = "/test/{userId}")
public class WebsocketServer {
    private static final Logger logger = LoggerFactory.getLogger(WebsocketServer.class);

    private String userId;

    /**
     * 连接时执行
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) throws IOException {
        this.userId = userId;
        logger.info("新连接：{}", userId);
    }

    /**
     * 关闭时执行
     */
    @OnClose
    public void onClose() {
        logger.info("连接：{} 关闭", this.userId);
    }

    /**
     * 收到消息时执行
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        logger.info("收到用户{}的消息：{}", this.userId, message);
        //回复客户端
        session.getBasicRemote().sendText("收到 " + this.userId + " 的消息 ");
    }

    /**
     * 连接错误时执行
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("用户id为：{}的连接发送错误", this.userId);
        error.printStackTrace();
    }

}