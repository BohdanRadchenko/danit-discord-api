package com.danit.discord.dto.user;

import com.danit.discord.dto.server.ServerResponse;
import com.danit.discord.entities.User;
import com.danit.discord.entities.UserProfile;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String userName;
    private String name;
    private String avatar;
    private List<ServerResponse> servers;

    public static UserResponse of(User user) {
        Optional<UserProfile> profileOpt = user
                .getProfiles()
                .stream()
                .filter(p -> p.getServer() == null)
                .findFirst();
        UserResponse response = UserResponse
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .servers(user.getOwneredServers().stream().map(ServerResponse::of).collect(Collectors.toList()))
                .build();
        if (profileOpt.isEmpty()) {
            return response;
        }
        UserProfile profile = profileOpt.get();
        response.setAvatar(profile.getAvatar());
        response.setName(profile.getName());
        return response;
    }
}
