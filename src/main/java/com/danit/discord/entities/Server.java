package com.danit.discord.entities;

import com.danit.discord.dto.server.ServerRequest;
import com.danit.discord.utils.NanoId;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "servers")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Server extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    @Column
    private String title;
    @Column(unique = true, nullable = false)
    private String link;
    @OneToMany(mappedBy = "server")
    private List<TextChannel> textChannels;

    public Server addTextChannel(TextChannel channel) {
        textChannels = textChannels == null ? new ArrayList<>() : textChannels;
        textChannels.add(channel);
        return this;
    }

    public static Server create(ServerRequest serverRequest, User user) {
        return Server
                .builder()
                .title(serverRequest.getTitle())
                .owner(user)
                .link(NanoId.generate())
                .build();
    }
}
