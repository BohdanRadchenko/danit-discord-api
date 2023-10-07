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
        UserResponse userResponse = UserResponse.of(user);
        return UserAuthResponse
                .builder()
                .id(userResponse.getId())
                .email(userResponse.getEmail())
                .userName(userResponse.getUserName())
                .name(userResponse.getName())
                .avatar(userResponse.getAvatar())
                .build();
    }
}
