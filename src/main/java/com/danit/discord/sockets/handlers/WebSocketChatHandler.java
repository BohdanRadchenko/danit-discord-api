package com.danit.discord.sockets.handlers;

import com.danit.discord.dto.message.MessageResponse;
import com.danit.discord.dto.room.RoomMessage;
import com.danit.discord.services.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler implements SubProtocolCapable {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketChatHandler.class);
    private static final ObjectMapper om = new ObjectMapper();
    @Lazy
    private final ChatService chatService;
    private final Map<String, Map<String, WebSocketSession>> sessions = new HashMap<>();

    private <V extends Object> String toJson(V value) {
        try {
            return om.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRoom(WebSocketSession session) {
        return (String) session.getAttributes().get("room");
    }

    private void checkRoom(WebSocketSession session) throws IOException {
        try {
            String room = getRoom(session);
            chatService.existByLink(room);
        } catch (Exception ex) {
            logger.error(String.format("Server check room exception %s", ex.getMessage()));
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }
    }

    private Map<String, WebSocketSession> getRoomSessions(WebSocketSession session) {
        String room = getRoom(session);
        Map<String, WebSocketSession> chatSessions = sessions
                .getOrDefault(room, new HashMap<String, WebSocketSession>());
        sessions.put(room, chatSessions);
        return chatSessions;
    }

    private void removeChatSessions(WebSocketSession session) {
        Map<String, WebSocketSession> roomSessions = getRoomSessions(session);
        roomSessions.remove(session.getId());
        String room = getRoom(session);
        sessions.put(room, roomSessions);
    }

    private <M extends Object> void emit(WebSocketSession session, M msg) {
        Map<String, WebSocketSession> room = getRoomSessions(session);
        room.forEach((k, v) -> {
            try {
                v.sendMessage(new TextMessage(toJson(msg)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Server connection...");
        checkRoom(session);
        Map<String, WebSocketSession> chatSessions = getRoomSessions(session);
        emit(session, "User connected");
        chatSessions.put(session.getId(), session);
        logger.info("Server connection opened");
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
                    emit(session, broadcast);
                }
            }
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage tm) throws Exception {
        RoomMessage rm = RoomMessage.from(tm);
        logger.info("Server received: {}", rm.toString());

        MessageResponse message = chatService.createMessage(getRoom(session), rm);

        String response = String.format("response from server to '%s'", HtmlUtils.htmlEscape(rm.toString()));
        logger.info("Server sends: {}", response);
        emit(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.info("Server transport error: {}", exception.getMessage());
    }

    @Override
    public List<String> getSubProtocols() {
        return Collections.singletonList("subprotocol.websocket");
    }
}
