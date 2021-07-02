### 使用方法
1、添加配置
```
@Configuration
public class WebSocketConfiguration {

    @Bean
    public WebSocketClientManager webSocketClientManager() {
        WebSocketConfig config = new WebSocketConfig();
        Map<String, WebSocketConfig.ClientConfig> map = new HashMap<>();
        WebSocketConfig.ClientConfig clientConfig = getClientConfig();
        map.put(clientConfig.getIp(), clientConfig);
        config.setClientConfigMap(map);
        return new WebSocketClientManager(config);
    }

    // 示例值，可从配置文件中取
    private WebSocketConfig.ClientConfig getClientConfig() {
        WebSocketConfig.ClientConfig config = new WebSocketConfig.ClientConfig();
        config.setIp("127.0.0.1");
        config.setPort("55400");
        config.setCertPath("private_daemon.p12");
        config.setCertPassWord("123456");
        return config;
    }
}
```
2、接收websocket服务端发过来的信息，实现MessageHandler，将其装载为一个Bean
```
@Component
public class ChiaMessageHandler implements MessageHandler {

    @Override
    public void onMessage(WebSocketClient client, String message) {

    }

}
```
3、客户端向websocket服务端发送信息
```
    @Autowired
    private WebSocketClientManager clientManager;


    // 获取对应服务器的websocket客户端，发送信息
    WebSocketClient client = clientManager.getClient("127.0.0.1", "55400","/test",params);

    client.send(paramStr);

```