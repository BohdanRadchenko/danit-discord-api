package com.danit.discord.entities;

import com.danit.discord.utils.NanoId;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "text_channels")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextChannel extends AbstractEntity {
    @Column(nullable = false)
    public String title;
    @ManyToOne
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;
    @Column(unique = true, nullable = false)
    private String link;

    public static TextChannel create(Server server, Chat chat) {
        return TextChannel
                .builder()
                .title("General")
                .server(server)
                .chat(chat)
                .link(NanoId.generate())
                .build();
    }

}