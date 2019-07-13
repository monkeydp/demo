package com.monkeydp.demo.websocket.server;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * @author iPotato
 * @date 2019/7/13
 */
@Controller
@MessageMapping("/chat")
public class ChatController {

    @MessageMapping("join-chat-room")
    @SendTo("/topic/public")
    public ChatMessage joinChatRoom(@Payload ChatMessage chatMessage,
                                    SimpMessageHeaderAccessor headerAccessor) {
        String username = chatMessage.getSender();
        // Add username in web socket session
        headerAccessor.getSessionAttributes()
                      .put("username", username);
        String content = String.format("Welcome %s to join our chat room", username);
        return chatMessage.setContent(content);
    }

    @MessageMapping("send-message")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}
