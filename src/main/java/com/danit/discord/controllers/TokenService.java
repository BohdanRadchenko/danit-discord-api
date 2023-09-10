package com.danit.discord.controllers;

import com.danit.discord.entities.Token;
import com.danit.discord.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public Token create(String tokenString) {
        Token token = Token
                .builder()
                .refreshTokenHash(tokenString)
                .build();
        return tokenRepository.save(token);
    }
}
