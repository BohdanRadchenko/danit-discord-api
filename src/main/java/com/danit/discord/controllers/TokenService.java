package com.danit.discord.controllers;

import com.danit.discord.entities.Token;
import com.danit.discord.entities.User;
import com.danit.discord.enums.TokenType;
import com.danit.discord.exceptions.NotFoundException;
import com.danit.discord.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    private void NotFoundByUser(User user) {
        throw new NotFoundException(String.format("Token with userId %d not found!", user.getId()));
    }

    public Boolean isExistByUser(User user) {
        return tokenRepository.existsByUserId(user);
    }

    public Token getByUser(User user) {
        Optional<Token> token = tokenRepository.findByUserId(user);
        if (token.isEmpty()) {
            NotFoundByUser(user);
        }
        return token.get();
    }

    public Token create(String tokenString, User user) {
        Token token = Token
                .builder()
                .refreshTokenHash(tokenString)
                .tokenType(TokenType.BEARER)
                .userId(user)
                .build();
        return tokenRepository.save(token);
    }

    public Token createOrUpdate(String tokenString, User user) {
        if (tokenRepository.existsByUserId(user)) {
            Optional<Token> optionalToken = tokenRepository.findByUserId(user);
            if (optionalToken.isEmpty()) {
                NotFoundByUser(user);
            }
            Token token = optionalToken.get();
            token.setRefreshTokenHash(tokenString);
            tokenRepository.save(token);
            return token;
        }
        return create(tokenString, user);
    }
}
