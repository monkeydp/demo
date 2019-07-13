package com.monkeydp.demo.websocket.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author iPotato
 * @date 2019/7/13
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private String sender;
    private MessageType type;
    @Setter
    private String content;

    public ChatMessage(String sender, MessageType type) {
        this.sender = sender;
        this.type = type;
    }

    public String getContent() {
        if (null == content) {
            return "";
        }
        return content;
    }

    public enum MessageType {
        /**
         * 消息类型
         */
        CHAT,
        JOIN,
        LEAVE
    }
}
