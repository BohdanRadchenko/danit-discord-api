package com.danit.discord.entities;

import com.danit.discord.dto.server.ServerRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "servers")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Server extends AbstractEntity {

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column
    private String title;

    public static Server of(ServerRequest serverRequest) {
        return Server.builder().title(serverRequest.getTitle()).build();
    }

}
