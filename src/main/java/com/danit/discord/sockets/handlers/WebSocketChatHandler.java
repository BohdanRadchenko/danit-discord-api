package com.danit.discord.sockets.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WebSocketChatHandler extends TextWebSocketHandler implements SubProtocolCapable {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketChatHandler.class);
    private final Map<String, Map<String, WebSocketSession>> sessions = new HashMap<>();

    private String getChatId(WebSocketSession session) {
        return (String) session.getAttributes().get("chat");
    }

    private Map<String, WebSocketSession> getChatSessions(WebSocketSession session) {
        String chat = getChatId(session);
        Map<String, WebSocketSession> chatSessions = sessions
                .getOrDefault(chat, new HashMap<String, WebSocketSession>());
        sessions.put(chat, chatSessions);
        return chatSessions;
    }

    private void removeChatSessions(WebSocketSession session) {
        Map<String, WebSocketSession> chatSessions = getChatSessions(session);
        chatSessions.remove(session.getId());
        String chatFromSessions = getChatId(session);
        sessions.put(chatFromSessions, chatSessions);
    }

    private <Message extends WebSocketMessage> void emit(WebSocketSession session, Message message) {
        Map<String, WebSocketSession> chat = getChatSessions(session);
        chat.forEach((k, v) -> {
            try {
                v.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Server connection opened");

        TextMessage message = new TextMessage("User connected");
        Map<String, WebSocketSession> chatSessions = getChatSessions(session);
        emit(session, message);
        chatSessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.info("Server connection closed: {}", status);
//        String chatId = getChatId(session);
//        emit(session, new TextMessage(status.toString()));
//        removeChatSessions(session);
    }

    @Scheduled(fixedRate = 100)
    void sendPeriodicMessages() throws IOException {

        for (Map<String, WebSocketSession> value : sessions.values()) {
            for (WebSocketSession session : value.values()) {
                if (session.isOpen()) {
                    String broadcast = "server periodic message " + LocalTime.now();
                    logger.info("Server sends: {}", broadcast);
                    emit(session, new TextMessage(broadcast));
                }
            }
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String request = message.getPayload();
        logger.info("Server received: {}", request);

        String response = String.format("response from server to '%s'", HtmlUtils.htmlEscape(request));
        logger.info("Server sends: {}", response);
        emit(session, new TextMessage(response));
    }

//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) {
//        logger.info("Server transport error: {}", exception.getMessage());
//    }

    @Override
    public List<String> getSubProtocols() {
        return Collections.singletonList("subprotocol.websocket");
    }
}
