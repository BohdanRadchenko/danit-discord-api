package com.danit.discord.entities;

import com.danit.discord.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tokens")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User userId;
    @Column(unique = true, name = "refresh_token_hash")
    public String refreshTokenHash;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;
}