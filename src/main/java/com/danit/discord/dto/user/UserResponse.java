package com.danit.discord.dto.user;

import com.danit.discord.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String userName;
    private String name;

    public static UserResponse of(User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .userName(user.getUsername())
                .build();
    }
}
