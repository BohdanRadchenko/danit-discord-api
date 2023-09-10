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
    private String firstName;
    private String lastName;

    public static UserResponse of(User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .userName(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
