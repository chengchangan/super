package com.cca.example.websocket.client;

import com.cca.core.websocket.client.WebSocketClientManager;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/2 10:00
 */
@RestController
@RequestMapping("/websocket")
public class SendSocketMsgController {

    @Autowired
    private WebSocketClientManager clientManager;


    @GetMapping("/sendMsg/{msg}")
    public void testSentMsg(@PathVariable String msg) {
        List<String> params = new ArrayList<>();
        params.add("123");

        WebSocketClient client = clientManager.getClient("127.0.0.1", "8080", "/websocketTest", params);
        client.send(msg);
    }

}
