package com.example.aircraftwar2024;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

public class GameWebSocketClient extends WebSocketClient {

    private static GameWebSocketClient instance;
    private static final Object lock = new Object();
    private static CountDownLatch latch = new CountDownLatch(1);

    private GameWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public static GameWebSocketClient getInstance(String uri) throws URISyntaxException {
        synchronized (lock) {
            if (instance == null) {
                instance = new GameWebSocketClient(new URI(uri));
            }
        }
        return instance;
    }

    public static void resetInstance() throws URISyntaxException {
        synchronized (lock) {
            if (instance != null) {
                instance = null;
            }
        }
    }


    @Override
    public void onOpen(ServerHandshake handshakedata) {
        // 连接打开时的处理逻辑
    }

    @Override
    public void onMessage(String message) {
        try {
            JSONObject json = new JSONObject(message);

            if (json.has("message")) {
                String msg = json.getString("message");

                if ("begin".equals(msg)) {
                    latch.countDown(); // 解除阻塞
                }

                // 处理其他消息类型
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // 连接关闭时的处理逻辑
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public void sendScore(int score) {
        try {
            JSONObject json = new JSONObject();
            json.put("message_type", "score");
            json.put("message", score);
            send(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEnd() {
        try {
            JSONObject json = new JSONObject();
            json.put("message_type", "end");
            send(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void awaitBegin() throws InterruptedException {
        latch.await(); // 阻塞直到收到 "begin" 消息
    }
}
