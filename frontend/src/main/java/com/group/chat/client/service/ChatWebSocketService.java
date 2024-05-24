package com.group.chat.client.service;

import java.net.URI;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import com.group.chat.client.view.ChatView;

public class ChatWebSocketService extends WebSocketClient {
    private ChatView chatView;

    public ChatWebSocketService(URI serverUri, ChatView chatView) {
        // Add RFC 6455 to avoid shakehand refusal
        super(serverUri, new Draft_6455());
        this.chatView = chatView;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        sendFrame("CONNECT", Map.of("accept-version", "1.1,1.2", "heart-beat", "10000,10000"), "");
        subscribe("/topic/public");
        chatView.onConnected();
    }

    @Override
    public void onMessage(String message) {
        chatView.handleMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        chatView.onDisconnected();
    }

    @Override
    public void onError(Exception ex) {
        chatView.showMessage("Error: " + ex.getMessage());
    }
    
    public void send(String destination, String message) {
        if (isOpen()) {
            Map<String, String> headers = Map.of("destination", destination);
            sendFrame("SEND", headers, message);
        }
    }

    private void sendFrame(String command, Map<String, String> headers, String body) {
        StringBuilder frame = new StringBuilder();
        frame.append(command).append("\n");
        headers.forEach((key, value) -> frame.append(key).append(":").append(value).append("\n"));
        frame.append("\n").append(body).append("\0");
        send(frame.toString());
    }

    private void subscribe(String destination) {
        if (isOpen()) {
            Map<String, String> headers = Map.of("destination", destination, "id", "sub-0", "ack", "auto");
            sendFrame("SUBSCRIBE", headers, "");
        }
    }
}
