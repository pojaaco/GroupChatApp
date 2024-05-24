package com.group.chat.client.view;

import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.*;

import com.google.gson.Gson;
import com.group.chat.client.model.ChatMessage;
import com.group.chat.client.service.ChatWebSocketService;


public class ChatView extends JFrame{
    private String username;
    private JTextArea messageArea;
    private JTextField textField;
    private ChatWebSocketService chatWebSocketClient;

    public ChatView(String username) {
        this.username = username;

        initUI();
        connectToWebSocket();
    }

    private void initUI() {
        setTitle("Chat Client - " + username);
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        textField = new JTextField();
        textField.setEditable(false);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                sendMessage(event.getActionCommand());
                textField.setText("");
            }
        });
        add(textField, BorderLayout.SOUTH);
    }

    private void connectToWebSocket() {
        try {
            chatWebSocketClient = new ChatWebSocketService(new URI("ws://localhost:8080/ws"), this);
            chatWebSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void enableUserText(boolean enabled) {
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    textField.setEditable(enabled);
                }
            }
        );
    }

    private void sendMessage(String messageContent) {
        if (chatWebSocketClient != null && chatWebSocketClient.isOpen()) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSender(username);
            chatMessage.setContent(messageContent);
            chatMessage.setType(ChatMessage.MessageType.CHAT);
            chatWebSocketClient.send("/app/chat.sendMessage", new Gson().toJson(chatMessage));
        }
    }

    private void sayJoined() {
        if (chatWebSocketClient != null && chatWebSocketClient.isOpen()) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSender(username);
            chatMessage.setType(ChatMessage.MessageType.JOIN);
            chatWebSocketClient.send("/app/chat.addUser", new Gson().toJson(chatMessage));
        }
    }

    private void sayLeft() {
        if (chatWebSocketClient != null && chatWebSocketClient.isOpen()) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSender(username);
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatWebSocketClient.send("/app/chat.deleteUser", new Gson().toJson(chatMessage));
        }
    }

    public void showMessage(String message) {
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    messageArea.append(message + "\n");
                }
            }
        );
    }

    public void handleMessage(String message) {
        try {
            String tmp = message.substring(message.indexOf('{'), message.indexOf('}')+1);
            ChatMessage chatMessage = new Gson().fromJson(tmp, ChatMessage.class);
            switch (chatMessage.getType()) {
                case CHAT:
                    showMessage(chatMessage.getSender() + ": " + chatMessage.getContent());
                    break;
                case JOIN:
                    showMessage(chatMessage.getSender() + " joined");
                    break;
                case LEAVE:
                    showMessage(chatMessage.getSender() + " left");
                    break;
            }
        } catch (Exception e) {

        }
    }

    public void onConnected() {
        enableUserText(true);
        sayJoined();
    }

    public void onDisconnected() {
        enableUserText(false);
        sayLeft();
    }

}