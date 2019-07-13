const ChatMessageType = Object.freeze({
    JOIN: "JOIN",
    CHAT: "CHAT",
    LEAVE: "LEAVE",
})

class ChatMessage {
    constructor(sender, type, content = "") {
        this.sender = sender
        this.type = type
        this.content = content
    }
}

class Chatter {

    constructor(url) {
        let socket = new SockJS(url);
        this._stompClient = Stomp.over(socket);
    }

    connect() {
        this._stompClient.connect(
            {},
            success => {
                this._subscribeAll()
            },
            error => {
                console.log("Could not connect to websocket server. Please refresh this page to try again!")
            }
        );
    }

    disconnect() {
        this._stompClient.disconnect(
            {},
            success => {
                console.log("Disconnect to websocket server succeeded!")
            },
            error => {
                console.log("Could not disconnect to websocket server!")
            }
        );
    }

    _subscribeAll() {
        this._subscribePublic()
    }

    _subscribePublic() {
        this._stompClient.subscribe("/topic/public", payload => {
            console.log("Do something after subscribe message arrived")
            let chatMessage = JSON.parse(payload.body);
            console.log(chatMessage.content)
            console.log("\n\n");
        });
    }

    _joinChatRoom(chatMessage) {
        this._stompClient.send("/app/chat/join-chat-room",
            {},
            JSON.stringify(chatMessage)
        )
    }

    _sendMessage(chatMessage) {
        this._stompClient.send("/app/chat/send-message",
            {},
            JSON.stringify(chatMessage)
        )
    }
}

{
    let chatter = new Chatter("http://localhost:8080/ws")
    chatter.connect()

    setTimeout(_ => {
        console.log("## Mock user to join the chat room")
        chatter._joinChatRoom(new ChatMessage(username, ChatMessageType.JOIN))
    }, 2000)

    setTimeout(_ => {
        console.log("## Mock user to send message")
        let content = "Hi everyone, I'm " + username
        chatter._sendMessage(new ChatMessage(username, ChatMessageType.CHAT, content))
    }, 4000)

    setTimeout(_ => {
        chatter.disconnect()
    }, 10000)
}