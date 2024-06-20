package com.example.aircraftwar2024.web;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

public class GameWebSocketClient extends WebSocketClient {

    private static GameWebSocketClient instance;

//    private static final Object lock = new Object();

    // 用于开始的 latch
    private static CountDownLatch beginLatch = new CountDownLatch(1);

    // 用于结束的 latch
    private static CountDownLatch endLatch = new CountDownLatch(1);

    private GameWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public static GameWebSocketClient getInstance(String uri) throws URISyntaxException {
        synchronized (GameWebSocketClient.class) {
            if (instance == null) {
                instance = new GameWebSocketClient(new URI(uri));
            }
        }
        return instance;
    }

    public static void resetInstance() throws URISyntaxException {
        synchronized (GameWebSocketClient.class) {
            if (instance != null) {
                instance = null;
                beginLatch = new CountDownLatch(1);
            }
        }
    }


    @Override
    public void onOpen(ServerHandshake handshakedata) {
        // 连接打开时的处理逻辑
    }

    @Override
    public void onMessage(String message) {
        ObjectMapper objectMapper = new ObjectMapper();

        /* ---------- GameBegin ---------- */
        try {
            // Attempt to deserialize to GameBegin
            GameBegin gameBegin = objectMapper.readValue(message, GameBegin.class);
            if ("begin".equals(gameBegin.getMessage())) {
                // Handle GameBegin message
                System.out.println("Game begin message received");
                beginLatch.countDown(); // 解除阻塞
                return;
            }
        } catch (Exception e) {
            // Ignore, try the next class
        }

        /* ---------- GameEnd ---------- */

        try {
            // Attempt to deserialize to GameEnd
            GameEnd gameEnd = objectMapper.readValue(message, GameEnd.class);
            // 收到了 end ，那么解锁
            if ("end".equals(gameEnd.getMessage())) {
                // Handle GameEnd message
                System.out.println("Game end message received with score: " + gameEnd.getScore());
                endLatch.countDown(); // 解除阻塞
                //
                return;
            }
        } catch (Exception e) {
            // Ignore, try the next class
        }

        /* ---------- Score ---------- */

        try {
            // Attempt to deserialize to Score
            Score score = objectMapper.readValue(message, Score.class);
            // Handle Score message
            System.out.println("Score message received with value: " + score.getMessage());

            //
        } catch (Exception e) {
            // Ignore if it fails
            System.err.println("Failed to deserialize message: " + message);
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
            // 创建 Score 对象并设置属性
            Score scoreObj = new Score(score);

            // 使用 Jackson 库进行序列化
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(scoreObj);

            // 发送 JSON 字符串
            send(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEnd(int score) {
        try {
            // 创建 GameEnd 对象并设置属性
            GameEnd gameEnd = new GameEnd(score);

            // 使用 Jackson 库进行序列化
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(gameEnd);

            // 发送 JSON 字符串
            send(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void awaitBegin() throws InterruptedException {
        beginLatch.await(); // 阻塞直到收到 "begin" 消息
    }

    public void awaitEnd() throws InterruptedException {
        endLatch.await(); // 阻塞直到收到 "begin" 消息
    }



}
