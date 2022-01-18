package io.boncray.component.websocket.client;

import cn.hutool.core.util.StrUtil;
import io.boncray.common.utils.KeyStoreLoader;
import io.boncray.component.websocket.client.config.SocketConnectConfig;
import io.boncray.component.websocket.client.config.WebSocketConfig;
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


    /**
     * 缓存socketClient
     * <p>
     * 外层map：服务器IP做key，map为 目标ip下，所有路径对应的，socket连接(一个应用下有多个websocket连接)
     * 内层map：ip + port + path + param 做key，value：服务器连接
     */
    private static final Map<String, Map<String, WebSocketClient>> CLIENT_MAP = new ConcurrentHashMap<>();
    /**
     * key：ip + port + path + param
     * value：此连接需要的配置信息
     */
    private static final ConcurrentHashMap<String, SocketConnectConfig> CONNECT_SERVER_CONFIG_MAP = new ConcurrentHashMap<>();
    /**
     * 目标服务器宕机，延迟定时重连
     * <p>
     * ip + port + path + param，拼接
     */
    private static final Set<String> WAIT_RETRY_CONNECTION_SERVER_PATH = Collections.synchronizedSet(new HashSet<>());

    private static final ScheduledExecutorService RETRY_CONNECT_EXECUTOR = Executors.newScheduledThreadPool(1,
            x -> new Thread(x, "baas.chia.remote.webSocket.WebSocketClientManager"));

    /**
     * 客户端全局配置信息
     */
    protected final WebSocketConfig globalConfig;

    /**
     * 收到server端返回的消息后，客户端对应的处理器
     */
    protected MessageHandler handler;

    public WebSocketClientManager(WebSocketConfig config) {
        this.globalConfig = config;
        RETRY_CONNECT_EXECUTOR.scheduleWithFixedDelay(new RetryConnector(), Math.max(config.getConnectErrorRetryInterval(), 3), config.getConnectErrorRetryInterval(), TimeUnit.MINUTES);
    }


    /**
     * 获取指定服务器websocket客户端
     *
     * @param ip     指定服务器IP
     * @param port   指定端口
     * @param path   指定路径
     * @param params 路径参数
     * @return
     */
    public WebSocketClient getClient(String ip, String port, String path, List<String> params) {
        SocketConnectConfig connectConfig = new SocketConnectConfig(ip, port, path, params);
        String fullPath = connectConfig.getFullPath();
        CONNECT_SERVER_CONFIG_MAP.put(fullPath, connectConfig);
        // 当前客户端，对于一个目标服务器下所持有的连接数，每个连接对应不同的path
        Map<String, WebSocketClient> pathClientMap = CLIENT_MAP.computeIfAbsent(ip, (x) -> new ConcurrentHashMap<>(20));
        return pathClientMap.computeIfAbsent(fullPath, this::createWebSocketClient);
    }

    /**
     * 失败重连
     *
     * @param fullPath 需要连接的服务器 Ip + port + path + params
     */
    protected WebSocketClient resetClientAndGet(String fullPath) {
        SocketConnectConfig connectConfig = CONNECT_SERVER_CONFIG_MAP.get(fullPath);
        Map<String, WebSocketClient> pathClientMap = CLIENT_MAP.get(connectConfig.getIp());
        WebSocketClient webSocketClient = pathClientMap.get(fullPath);

        if (webSocketClient.isOpen()) {
            return webSocketClient;
        }
        synchronized (fullPath.intern()) {
            webSocketClient = pathClientMap.get(fullPath);
            if (webSocketClient.isOpen()) {
                return webSocketClient;
            }
            webSocketClient = this.createWebSocketClient(fullPath);
            pathClientMap.put(fullPath, webSocketClient);
        }
        return webSocketClient;
    }

    /**
     * 将失败的连接加入到等待重连队列中
     */
    protected void waitRetryConnect(String fullPath) {
        WAIT_RETRY_CONNECTION_SERVER_PATH.add(fullPath);
    }


    /**
     * 创建websocket 客户端连接
     *
     * @param fullPath 需要连接的服务器 Ip + port + path + params
     * @return
     */
    private WebSocketClient createWebSocketClient(String fullPath) {
        try {
            WebSocketClient webSocketClient = new CustomWebSocketClient(this, buildUri(fullPath));
            // 使用ssl
            ssl(webSocketClient);
            webSocketClient.connect();
            return webSocketClient;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("webSocketClient create failed，reason：" + e.getMessage());
        }
    }

    /**
     * 构建连接websocket url 连接
     *
     * @param fullPath 需要连接的服务器 Ip + port + path + params
     * @return
     * @throws URISyntaxException
     */
    private URI buildUri(String fullPath) throws URISyntaxException {
        SocketConnectConfig connectConfig = CONNECT_SERVER_CONFIG_MAP.get(fullPath);
        Map<String, WebSocketConfig.SslConfig> configMap = globalConfig.getSslConfigMap();

        StringBuilder url = new StringBuilder();
        // 不使用ssl 连接websocket
        if (CollectionUtils.isEmpty(configMap) || configMap.get(connectConfig.getHost()) == null) {
            url.append("ws://");
        } else {
            WebSocketConfig.SslConfig sslConfig = configMap.get(connectConfig.getHost());
            if (StrUtil.isBlank(sslConfig.getCertPath())) {
                throw new IllegalArgumentException("configured SslConfig, but the certPath is empty");
            }
            url.append("wss://");
        }
        url.append(fullPath);
        return new URI(url.toString());
    }


    /**
     * 配置SSL信息
     *
     * @param webSocketClient
     * @throws Exception
     */
    private void ssl(WebSocketClient webSocketClient) throws Exception {
        URI uri = webSocketClient.getURI();
        if ("wss".equals(uri.getScheme())) {
            WebSocketConfig.SslConfig sslConfig = globalConfig.getSslConfigMap().get(uri.getAuthority());
            String certPath = sslConfig.getCertPath();
            String certPassWord = sslConfig.getCertPassWord();

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
            Iterator<String> iterator = WAIT_RETRY_CONNECTION_SERVER_PATH.iterator();
            while (iterator.hasNext()) {
                String fullPath = iterator.next();
                iterator.remove();
                LOGGER.info("remote server broken connect ，retry connect now ，url:{}，", fullPath);
                resetClientAndGet(fullPath);
            }
        }
    }

}
