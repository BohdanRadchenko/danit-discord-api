package com.danit.discord.dto.user;

import com.danit.discord.dto.server.ServerResponse;
import com.danit.discord.entities.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String userName;
    private String name;
    private String avatar;
    private List<ServerResponse> servers;

    public static UserResponse of(User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .userName(user.getUserName())
                .avatar(user.getAvatar())
                .servers(user.getOwneredServers().stream().map(ServerResponse::of).collect(Collectors.toList()))
                .build();
    }
}
