package com.danit.discord.dto.channel;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChannelInviteRequest {
    @NotNull(message = "Invalid id: id is NULL")
    @Min(1)
    private Long id;
}
