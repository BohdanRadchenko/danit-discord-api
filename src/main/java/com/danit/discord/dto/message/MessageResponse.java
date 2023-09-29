package com.danit.discord.dto.message;

import com.danit.discord.dto.user.UserMessageResponse;
import com.danit.discord.entities.Message;
import com.danit.discord.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private UserMessageResponse from;
    private MessageType messageType;
    private String message;

    public static MessageResponse of(Message msg) {
        return MessageResponse
                .builder()
                .from(UserMessageResponse.of(msg.getFrom()))
                .messageType(msg.getMessageType())
                .message(msg.getMessage())
                .build();
    }
}
