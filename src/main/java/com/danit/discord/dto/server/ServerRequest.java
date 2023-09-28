package com.danit.discord.dto.server;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ServerRequest {
    @NotNull
    @NotBlank(message = "Invalid server title: Empty server title")
    @NotNull(message = "Invalid server title: server title is NULL")
    @Size(min = 3, max = 16, message = "Invalid server title: Must be of 3 - 16 characters")
    private String title;
}
