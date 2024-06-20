package com.example.aircraftwar2024.web;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.example.aircraftwar2024.web.structs.GameBegin;
import com.example.aircraftwar2024.web.structs.GameEnd;
import com.example.aircraftwar2024.web.structs.Score;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class WebSocketService {

    public static final String TAG = "WebSocketService";

    private WebSocketClient webSocketClient;

    private static WebSocketService m_instance = new WebSocketService();
    private ObjectMapper objectMapper = new ObjectMapper();
    private BlockingQueue<GameBegin> gameBeginQueue = new ArrayBlockingQueue<>(1);
    private BlockingQueue<GameEnd> gameEndQueue = new ArrayBlockingQueue<>(1);
    private BlockingQueue<Score> scoreQueue = new ArrayBlockingQueue<>(10);

    private WebSocketService() {
    }

    static public WebSocketService getInstance() {
        return m_instance;
    }

    public void connect(String uri) throws Exception {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            throw new IllegalStateException("WebSocket is already connected");
        }

        webSocketClient = new WebSocketClient(new URI(uri)) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.v(TAG, "WebSocket opened");
            }

            @Override
            public void onMessage(String message) {
                try {
                    JsonNode jsonNode = objectMapper.readTree(message);
                    if (jsonNode.has("message")) {
                        String msgType = jsonNode.get("message").asText();
                        if ("begin".equals(msgType)) {
                            GameBegin gameBegin = objectMapper.treeToValue(jsonNode, GameBegin.class);
                            gameBeginQueue.put(gameBegin);
                            Log.d(TAG, "Received GameBegin message: " + gameBegin.getMessage());
                        } else if ("end".equals(msgType)) {
                            GameEnd gameEnd = objectMapper.treeToValue(jsonNode, GameEnd.class);
                            gameEndQueue.put(gameEnd);
                            Log.d(TAG, "Received GameEnd message: " + gameEnd.getScore());
                        }
                    } else if (jsonNode.has("score")) {
                        Score score = objectMapper.treeToValue(jsonNode, Score.class);
                        scoreQueue.put(score);
                        Log.d(TAG, "Received Score message: " + score.getMessage());
                    } else {
                        Log.d(TAG, "Unknown message type received: " + message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.v(TAG, "WebSocket closed with exit code " + code + " additional info: " + reason);
                // 重置状态
                m_instance = new WebSocketService();
                objectMapper = new ObjectMapper();
                gameBeginQueue = new ArrayBlockingQueue<>(1);
                gameEndQueue = new ArrayBlockingQueue<>(1);
                scoreQueue = new ArrayBlockingQueue<>(10);
            }

            @Override
            public void onError(Exception ex) {
                Log.v(TAG, "WebSocket error: " + ex.getMessage());
                m_instance = null;
                objectMapper = null;
                gameBeginQueue = null;
                gameEndQueue = null;
                scoreQueue = null;
            }
        };

        webSocketClient.connect();
    }

    /**
     * 阻塞的接受 Begin
     */
    public GameBegin recvBegin() throws InterruptedException {
        return gameBeginQueue.take();
    }

    /**
     * 阻塞的接受 End
     */
    public GameEnd recvEnd() throws InterruptedException {
        return gameEndQueue.take();
    }

    /**
     * 不阻塞的接受 score
     */
    public Score tryRecvScore() {
        return scoreQueue.poll();
    }

    public void sendGameBegin(GameBegin gameBegin) throws Exception {
        sendMessage(gameBegin);
    }

    public void sendGameEnd(GameEnd gameEnd) throws Exception {
        sendMessage(gameEnd);
    }

    public void sendScore(Score score) throws Exception {
        sendMessage(score);
    }

    private void sendMessage(Object message) throws Exception {
        if (webSocketClient == null || !webSocketClient.isOpen()) {
            throw new IllegalStateException("WebSocket is not connected");
        }

        String jsonMessage = objectMapper.writeValueAsString(message);
        webSocketClient.send(jsonMessage);
    }

    public void disconnect() {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.close();
        }
    }
}
