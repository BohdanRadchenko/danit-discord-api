package com.danit.discord.dto.text;

import com.danit.discord.entities.TextChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextChannelServerResponse {
    private String title;
    private String link;

    public static TextChannelServerResponse of(TextChannel channel) {
        return TextChannelServerResponse
                .builder()
                .title(channel.getTitle())
                .link(channel.getLink())
                .build();
    }
}
