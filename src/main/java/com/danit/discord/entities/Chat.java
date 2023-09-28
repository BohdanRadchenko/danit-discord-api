package com.danit.discord.entities;

import com.danit.discord.utils.NanoId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "chats")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chat extends AbstractEntity {
    @Column(unique = true, nullable = false)
    private String link;

    public static Chat create() {
        return Chat
                .builder()
                .link(NanoId.generate())
                .build();
    }
}