package com.danit.discord.dto.text;

import com.danit.discord.dto.server.ServerResponse;
import com.danit.discord.entities.Chat;
import com.danit.discord.entities.TextChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextChannelResponse {
    private Long id;
    private String title;
    private ServerResponse server;
    private Chat chat;
    private String link;

    public static TextChannelResponse of(TextChannel channel) {
        return TextChannelResponse
                .builder()
                .id(channel.getId())
                .title(channel.getTitle())
                .link(channel.getLink())
                .server(ServerResponse.of(channel.getServer()))
                .chat(channel.getChat())
                .build();
    }
}
