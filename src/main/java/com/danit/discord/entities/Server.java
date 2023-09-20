package com.danit.discord.entities;

import com.danit.discord.dto.server.ServerRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity(name = "servers")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Server extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
    @Column
    private String title;

    public static Server of(ServerRequest serverRequest, User user) {
        return Server
                .builder()
                .title(serverRequest.getTitle())
                .owner(user)
                .build();
    }

}
