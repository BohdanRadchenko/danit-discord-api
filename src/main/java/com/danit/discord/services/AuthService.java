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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final AuthJwtService authJwtService;
    private final UserService userService;
    private final TokenService tokenService;
    private final BCryptService bCryptService;

    private AuthResponse generateTokensResponse(User user) {
        String accessToken = authJwtService.generateAccessToken(user);
        String refreshToken = authJwtService.generateRefreshToken(user);
        tokenService.create(bCryptService.encode(refreshToken));
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
        } catch (Exception ignored) {
            throw new LoginException();
        }
    }

//        Authentication authentication =
//                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//        System.out.println("authentication " + authentication);
//        String email = authentication.getName();
//        User user = new User(email, "");
//        User user = User.builder().email(email).build();

//        return generateTokensResponse(user);
}
