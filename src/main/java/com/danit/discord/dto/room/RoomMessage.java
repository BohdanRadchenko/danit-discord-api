package com.danit.discord.dto.room;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.socket.TextMessage;

@Data
@ToString
public class RoomMessage {
    private static final ObjectMapper om = new ObjectMapper();
    private Long from;
    private String message;

    public String to() {
        try {
            return om.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static RoomMessage from(TextMessage tm) {
        try {
            return om.readValue(tm.getPayload(), RoomMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
