package com.danit.discord.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {
    @NotNull
    @NotBlank(message = "Email is mandatory")
    @Email(regexp = ".*@.*\\..*", message = "Email should be valid")
    private String email;

    @NotBlank(message = "Invalid password: empty password")
    @NotNull(message = "Invalid password: password is NULL")
    @Size(min = 8, max = 30, message = "Invalid password: Must be of 8 - 30 characters")
    private String password;
}
