package com.danit.discord.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull
    @NotBlank(message = "Email is mandatory")
    @Email(regexp = ".*@.*\\..*", message = "Email should be valid")
    private String email;

    @NotNull
    @NotBlank(message = "Invalid username: Empty username")
    @NotNull(message = "Invalid username: username is NULL")
    @Size(min = 3, max = 30, message = "Invalid username: Must be of 3 - 30 characters")
    private String username;

    @NotBlank(message = "Invalid name: Empty name")
    @NotNull(message = "Invalid name: name is NULL")
    @Size(min = 3, max = 16, message = "Invalid name: Must be of 3 - 16 characters")
    private String name;

    @NotBlank(message = "Invalid password: password name")
    @NotNull(message = "Invalid password: password is NULL")
    @Size(min = 8, max = 30, message = "Invalid password: Must be of 8 - 30 characters")
    private String password;

//    @NotNull(message = "Invalid dateOfBirth: dateOfBirth is NULL")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//    @JsonFormat(pattern = "yyyy/MM/dd")
//    private LocalDate dateOfBirth;
}
