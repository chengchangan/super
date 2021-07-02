package com.cca.core.websocket.client;

import lombok.SneakyThrows;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


/**
 * @author cca
 * @version 1.0
 * @date 2021/5/22 17:04
 */
public class CustomWebSocketClient extends WebSocketClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomWebSocketClient.class);

    private static final ThreadLocal<Integer> RETRY_TIME_LOCAL = new ThreadLocal<>();

    private final Integer CONNECT_TIME_OUT;
    /**
     * 连接失败，重试次数
     */
    private final Integer TIME_OUT_RETRY_TIME;


    WebSocketClientManager clientManager;

    public CustomWebSocketClient(WebSocketClientManager manager, URI serverUri) {
        super(serverUri);
        this.clientManager = manager;
        CONNECT_TIME_OUT = manager.globalConfig.getConnectTimeout();
        TIME_OUT_RETRY_TIME = manager.globalConfig.getTimeoutRetryTimes();
    }

    @Override
    public void onOpen(ServerHandshake shake) {
        LOGGER.info("url：{}，websocket connect succeed", uri.getAuthority() + uri.getPath());
    }

    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
        LOGGER.info("WebSocket onClose ，remote server ip：{}，cause：{}", uri.getAuthority() + uri.getPath(), arg1);
        if (onCloseRetryConnect()) {
            clientManager.waitRetryConnect(uri.getAuthority() + uri.getPath());
        }
    }

    public boolean onCloseRetryConnect() {
        return true;
    }

    @Override
    public void onError(Exception exception) {
        LOGGER.info("WebSocket onError，remote server ip：{}，exception：", uri.getAuthority() + uri.getPath(), exception);
        if (onErrorRetryConnect()) {
            clientManager.waitRetryConnect(uri.getAuthority() + uri.getPath());
        }
    }

    public boolean onErrorRetryConnect() {
        return true;
    }

    @Override
    public void onMessage(String message) {
        LOGGER.debug("remote server ip：{}，receive data：{}", uri.getAuthority() + uri.getPath(), message);
        clientManager.handler.onMessage(this, message);
    }


    @SneakyThrows
    @Override
    public void send(String text) {
        long startTime = System.currentTimeMillis();
        if (!isOpen()) {
            try {
                synchronized (this) {
                    while (!isOpen()) {
                        if (checkTimeout(startTime, CONNECT_TIME_OUT, TimeUnit.SECONDS)) {
                            Integer currentRetryTime = Optional.ofNullable(RETRY_TIME_LOCAL.get()).orElse(0);
                            if (currentRetryTime >= TIME_OUT_RETRY_TIME) {
                                throw new RuntimeException("can not connect target server，ip：" + uri.getAuthority() + uri.getPath());
                            }
                            // 如果当前socket连接超时，则重置这个连接再次发送，结束当前发送
                            WebSocketClient client = clientManager.resetClientAndGet(uri.getAuthority() + uri.getPath());
                            RETRY_TIME_LOCAL.set(currentRetryTime + 1);
                            LOGGER.info("connect timeout，retry times：{}", RETRY_TIME_LOCAL.get());
                            client.send(text);
                            return;
                        }
                        LOGGER.debug("wait open connect，url：{}", uri.getAuthority() + uri.getPath());
                        this.wait(1000);
                    }
                }
            } finally {
                RETRY_TIME_LOCAL.remove();
            }
        }
        LOGGER.debug("send websocket request，param：{}", text);
        super.send(text);
    }

    private Boolean checkTimeout(Long startTimeStamp, long timeout, TimeUnit unit) {
        return System.currentTimeMillis() > (unit.toMillis(timeout) + startTimeStamp);
    }
}
