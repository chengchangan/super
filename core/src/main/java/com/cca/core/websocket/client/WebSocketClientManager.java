package com.cca.core.websocket.client;

import com.cca.core.util.KeyStoreLoader;
import org.apache.commons.lang3.StringUtils;
import org.java_websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author cca
 * @version 1.0
 * @date 2021/5/22 10:26
 */
public class WebSocketClientManager implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketClientManager.class);


    private static final ConcurrentHashMap<String, WebSocketClient> CLIENT_MAP = new ConcurrentHashMap<>();
    /**
     * 目标服务器宕机，延迟定时重连
     */
    private static final Set<String> WAIT_RETRY_CONNECTION_SERVER_IP = Collections.synchronizedSet(new HashSet<>());

    private static final ScheduledExecutorService RETRY_CONNECT_EXECUTOR = Executors.newScheduledThreadPool(1,
            x -> new Thread(x, "baas.chia.remote.webSocket.WebSocketClientManager"));

    protected final WebSocketConfig config;

    protected MessageHandler handler;

    public WebSocketClientManager(WebSocketConfig config) {
        this.config = config;
        RETRY_CONNECT_EXECUTOR.scheduleWithFixedDelay(new RetryConnector(), 10, config.getConnectErrorRetryInterval(), TimeUnit.MINUTES);
    }


    /**
     * 获取指定服务器websocket客户端
     *
     * @param ip 指定服务器IP
     * @return
     */
    public WebSocketClient getClient(String ip) {
        return CLIENT_MAP.computeIfAbsent(ip, this::createWebSocketClient);
    }

    /**
     * 失败重连
     *
     * @param ip 需要连接的服务器Ip
     */
    protected WebSocketClient resetClientAndGet(String ip) {
        WebSocketClient webSocketClient = CLIENT_MAP.get(ip);
        if (webSocketClient.isOpen()) {
            return webSocketClient;
        }
        synchronized (ip.intern()) {
            webSocketClient = CLIENT_MAP.get(ip);
            if (webSocketClient.isOpen()) {
                return webSocketClient;
            }
            webSocketClient = this.createWebSocketClient(ip);
            CLIENT_MAP.put(ip, webSocketClient);
        }
        return webSocketClient;
    }

    protected void waitRetryConnect(String remoteServerIp) {
        WAIT_RETRY_CONNECTION_SERVER_IP.add(remoteServerIp);
    }


    private WebSocketClient createWebSocketClient(String ip) {
        try {
            WebSocketClient webSocketClient = new CustomWebSocketClient(this, buildUri(ip));
            // 使用ssl
            ssl(webSocketClient);
            webSocketClient.connect();
            return webSocketClient;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("webSocketClient creat failed，reason：" + e.getMessage());
        }
    }

    private URI buildUri(String ip) throws URISyntaxException {
        Map<String, WebSocketConfig.ClientConfig> configMap = config.getClientConfigMap();
        if (CollectionUtils.isEmpty(configMap) || configMap.get(ip) == null) {
            throw new RuntimeException("连接的服务端Ip：" + ip + ",配置不存在");
        }
        WebSocketConfig.ClientConfig clientConfig = configMap.get(ip);

        StringBuilder sb = new StringBuilder();
        if (StringUtils.isBlank(clientConfig.getCertPath())) {
            sb.append("ws://");
        } else {
            sb.append("wss://");
        }
        String uri = sb.append(clientConfig.getIp()).append(":").append(clientConfig.getPort()).toString();
        return new URI(uri);
    }

    private void ssl(WebSocketClient webSocketClient) throws Exception {
        URI uri = webSocketClient.getURI();
        if ("wss".equals(uri.getScheme())) {
            WebSocketConfig.ClientConfig clientConfig = config.getClientConfigMap().get(uri.getHost());
            String certPath = clientConfig.getCertPath();
            String certPassWord = clientConfig.getCertPassWord();

            KeyStore keyStore;
            try (InputStream in = WebSocketClientManager.class.getClassLoader().getResourceAsStream(certPath)) {
                if (in == null) {
                    throw new IllegalArgumentException("服务端：" + uri.getHost() + "，无法找到证书");
                }
                keyStore = KeyStoreLoader.load(in, "PKCS12", certPassWord);
            }

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(keyStore, certPassWord.toCharArray());

            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            webSocketClient.setSocket(sslContext.getSocketFactory().createSocket());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.handler = applicationContext.getBean(MessageHandler.class);
    }


    /**
     * 目标服务挂掉，延迟定时重连
     */
    private class RetryConnector implements Runnable {

        @Override
        public void run() {
            Iterator<String> iterator = WAIT_RETRY_CONNECTION_SERVER_IP.iterator();
            while (iterator.hasNext()) {
                String remoteServerIp = iterator.next();
                iterator.remove();
                LOGGER.info("服务器：{}，连接断开，开始重连", remoteServerIp);
                resetClientAndGet(remoteServerIp);
            }
        }
    }

}
