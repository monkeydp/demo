package com.monkeydp.demo.websocket.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

import static com.monkeydp.demo.websocket.server.ChatMessage.MessageType.CHAT;
import static com.monkeydp.demo.websocket.server.ChatMessage.MessageType.LEAVE;

/**
 * @author iPotato
 * @date 2019/7/13
 */
@Slf4j
@Component
public class WebsocketEventListener {

    private final SimpMessageSendingOperations operations;

    @Autowired
    public WebsocketEventListener(SimpMessageSendingOperations operations) {
        this.operations = operations;
    }

    @EventListener
    public void handleWebsocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new websocket connection");
    }

    @EventListener
    public void handleWebsocketDisconnectListener(SessionDisconnectEvent event) {
        String username = this.getSessionAttribute(event, "username");
        if (username != null) {
            log.info("User Disconnected : " + username);

            ChatMessage chatMessage = new ChatMessage(username, LEAVE);
            operations.convertAndSend("/topic/public", chatMessage);
        }
    }

    /**
     * Uncomment @Scheduled to enable scheduler
     */
//    @Scheduled(fixedRate = 2000)
    public void autoSendMessage() {
        ChatMessage chatMessage = new ChatMessage("Websocket Server", CHAT, "Server auto send a message to client");
        operations.convertAndSend("/topic/public", chatMessage);
    }

    private Map<String, Object> getSessionAttributes(AbstractSubProtocolEvent event) {
        StompHeaderAccessor headerAccessor =
                StompHeaderAccessor.wrap(event.getMessage());
        return headerAccessor.getSessionAttributes();
    }

    private <T> T getSessionAttribute(AbstractSubProtocolEvent event, String key) {
        Map<String, Object> sessionAttributes = this.getSessionAttributes(event);
        return (T) sessionAttributes.get(key);
    }
}
