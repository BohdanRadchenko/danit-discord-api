package com.danit.discord.entities;

import com.danit.discord.enums.InviteType;
import com.danit.discord.enums.StatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "invites")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invite extends AbstractEntity{
    @Column(unique = true, nullable = false)
    private Long fromId;
    @Column(unique = true, nullable = false)
    private Long toId;
    @Column(unique = true, nullable = false)
    private InviteType inviteType;
    @Column(unique = true, nullable = false)
    private StatusType status;
}
