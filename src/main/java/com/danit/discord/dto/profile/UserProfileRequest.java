package com.danit.discord.dto.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileRequest {
    @NotBlank(message = "Invalid name: empty name")
    @NotNull(message = "Invalid name: name is NULL")
    private String name;
    private String nickname;
    private String description;
    private String banner;
}
