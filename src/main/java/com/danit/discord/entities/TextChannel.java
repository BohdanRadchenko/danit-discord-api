package com.danit.discord.entities;

import com.danit.discord.utils.NanoId;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @ManyToMany
    @JoinTable(name = "text-user",
            inverseJoinColumns = @JoinColumn(name = "user_id",
                    nullable = false,
                    updatable = false),
            joinColumns = @JoinColumn(name = "channel_id",
                    nullable = false,
                    updatable = false),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private final List<User> users = new ArrayList<>();

    public TextChannel addUser(User user) {
        users.add(user);
        return this;
    }

    public static TextChannel create(Server server, Chat chat, User user) {
        TextChannel channel = TextChannel
                .builder()
                .title("General")
                .server(server)
                .chat(chat)
                .link(NanoId.generate())
                .build();
        channel.addUser(user);
        return channel;
    }

}