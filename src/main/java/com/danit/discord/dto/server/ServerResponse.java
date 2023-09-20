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
public class ServerResponse {

    private Long id;
    private String title;

    public static ServerResponse of(Server server) {
        return ServerResponse
                .builder()
                .id(server.getId())
                .title(server.getTitle())
                .build();
    }
}
