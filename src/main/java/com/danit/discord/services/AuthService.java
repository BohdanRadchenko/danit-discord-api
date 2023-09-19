package com.danit.discord.services;

import com.danit.discord.controllers.TokenService;
import com.danit.discord.dto.auth.AuthResponse;
import com.danit.discord.dto.auth.LoginRequest;
import com.danit.discord.dto.auth.RegisterRequest;
import com.danit.discord.entities.User;
import com.danit.discord.exceptions.AlreadyExistException;
import com.danit.discord.exceptions.BadRequestException;
import com.danit.discord.exceptions.LoginException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {
    private final AuthJwtService authJwtService;
    private final UserService userService;
    private final TokenService tokenService;
    private final BCryptService bCryptService;

    private void authenticate(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPasswordHash());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private AuthResponse generateTokensResponse(User user) {
        authenticate(user);
        String accessToken = authJwtService.generateAccessToken(user);
        String refreshToken = authJwtService.generateRefreshToken(user);
        tokenService.create(bCryptService.encode(refreshToken), user);
        return AuthResponse.of(accessToken, refreshToken, user);
    }

    public AuthResponse register(RegisterRequest registerRequest) throws AlreadyExistException {
        registerRequest.setPassword(bCryptService.encode(registerRequest.getPassword()));
        User user = userService.create(registerRequest);
        return generateTokensResponse(user);
    }

    public AuthResponse login(LoginRequest loginRequest) throws LoginException {
        try {
            User user = userService.getByEmail(loginRequest.getEmail());

            Boolean isPasswordMatches = bCryptService.match(loginRequest.getPassword(), user.getPasswordHash());
            if (!isPasswordMatches) {
                throw new BadRequestException("Invalid password");
            }
            return generateTokensResponse(user);
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            throw new LoginException();
        }
    }
}
