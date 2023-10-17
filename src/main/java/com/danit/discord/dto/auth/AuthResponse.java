package com.danit.discord.dto.auth;

import com.danit.discord.dto.user.UserAuthResponse;
import com.danit.discord.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("user")
    private UserAuthResponse user;

    public static AuthResponse of(String accessToken, String refreshToken, User user) {
        return AuthResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(UserAuthResponse.of(user))
                .build();
    }
}