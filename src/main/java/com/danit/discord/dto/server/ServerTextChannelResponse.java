package com.danit.discord.dto.server;

import com.danit.discord.entities.Server;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServerTextChannelResponse {
    private String title;
    private Long owner;
    private String link;

    public static ServerTextChannelResponse of(Server server) {
        return ServerTextChannelResponse
                .builder()
                .title(server.getTitle())
                .owner(server.getOwner().getId())
                .link(server.getLink())
                .build();
    }
}
