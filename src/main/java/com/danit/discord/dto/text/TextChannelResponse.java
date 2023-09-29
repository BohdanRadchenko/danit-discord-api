package com.danit.discord.dto.text;

import com.danit.discord.constants.Api;
import com.danit.discord.dto.message.MessageResponse;
import com.danit.discord.dto.server.ServerTextChannelResponse;
import com.danit.discord.entities.TextChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextChannelResponse {
    private String title;
    private ServerTextChannelResponse server;
    private String link;
    private String connect;
    private List<MessageResponse> messages;

    private static String createConnectLink(String link) {
        return String.format("%s/%s", Api.ROOM, link);
    }

    public static TextChannelResponse of(TextChannel channel) {
        return TextChannelResponse
                .builder()
                .title(channel.getTitle())
                .link(channel.getLink())
                .server(ServerTextChannelResponse.of(channel.getServer()))
                .connect(createConnectLink(channel.getChat().getLink()))
                .messages(channel
                        .getChat()
                        .getMessages()
                        .stream()
                        .map(MessageResponse::of)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
