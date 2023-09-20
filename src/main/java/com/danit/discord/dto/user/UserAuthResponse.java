package com.danit.discord.dto.user;

import com.danit.discord.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthResponse {
    private Long id;
    private String email;
    private String userName;
    private String name;
    private String avatar;

    public static UserAuthResponse of(User user) {
        return UserAuthResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .userName(user.getUsername())
                .avatar(user.getAvatar())
                .build();
    }
}
