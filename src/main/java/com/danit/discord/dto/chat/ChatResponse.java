package com.danit.discord.dto.chat;

import com.danit.discord.entities.Chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {
    private String link;

    public static ChatResponse of(Chat chat) {
        return ChatResponse
                .builder()
                .link(chat.getLink())
                .build();
    }
}
