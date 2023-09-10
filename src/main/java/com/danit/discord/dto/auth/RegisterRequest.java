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

    @NotBlank(message = "Invalid password: password name")
    @NotNull(message = "Invalid password: password is NULL")
    @Size(min = 8, max = 30, message = "Invalid password: Must be of 8 - 30 characters")
    private String password;

    @NotBlank(message = "Invalid first name: Empty first name")
    @NotNull(message = "Invalid first name: first name is NULL")
    @Size(min = 3, max = 16, message = "Invalid first name: Must be of 3 - 30 characters")
    private String firstName;

    @NotBlank(message = "Invalid last name: Empty last name")
    @NotNull(message = "Invalid last name: last name is NULL")
    @Size(min = 3, max = 16, message = "Invalid last name: Must be of 3 - 30 characters")
    private String lastName;
}
