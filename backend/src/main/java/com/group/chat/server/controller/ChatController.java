package com.group.chat.server.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.group.chat.server.model.ChatMessage;


@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        // The chatMessage parameter is automatically converted from JSON
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.deleteUser")
    @SendTo("/topic/public")
    public ChatMessage deleteUserUser(ChatMessage chatMessage) {
        return chatMessage;
    }
    
}
