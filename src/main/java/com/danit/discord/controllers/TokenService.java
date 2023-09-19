package com.danit.discord.controllers;

import com.danit.discord.entities.Token;
import com.danit.discord.entities.User;
import com.danit.discord.enums.TokenType;
import com.danit.discord.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public Token create(String tokenString, User user) {
        Token token = Token
                .builder()
                .refreshTokenHash(tokenString)
                .tokenType(TokenType.BEARER)
                .userId(user)
                .build();
        return tokenRepository.save(token);
    }
}
