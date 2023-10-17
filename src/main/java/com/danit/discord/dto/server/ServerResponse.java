package com.danit.discord.dto.server;

import com.danit.discord.dto.text.TextChannelServerResponse;
import com.danit.discord.entities.Server;
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
public class ServerResponse {
    private String title;
    private Long owner;
    private String link;
    private List<TextChannelServerResponse> textChannels;

    public static ServerResponse of(Server server) {
        return ServerResponse
                .builder()
                .title(server.getTitle())
                .owner(server.getOwner().getId())
                .link(server.getLink())
                .textChannels(server
                        .getTextChannels()
                        .stream()
                        .map(TextChannelServerResponse::of)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
