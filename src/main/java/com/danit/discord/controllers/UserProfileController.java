package com.danit.discord.controllers;

import com.danit.discord.annotations.ApiPrefix;
import com.danit.discord.constants.Api;
import com.danit.discord.dto.ResponseSuccess;
import com.danit.discord.dto.auth.AuthResponse;
import com.danit.discord.dto.auth.LoginRequest;
import com.danit.discord.dto.auth.RegisterRequest;
import com.danit.discord.dto.user.UserAuthResponse;
import com.danit.discord.services.AuthService;
import com.danit.discord.utils.Logging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@ApiPrefix(Api.PROFILE)
@AllArgsConstructor
public class UserProfileController {
    private final AuthService authService;
    private final Logging logger = Logging.of(UserProfileController.class);

    @Operation(summary = "Registration user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @PostMapping(Api.REGISTER)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseSuccess<AuthResponse> register(@RequestBody @Valid RegisterRequest registerRequest, HttpServletRequest req) {
        logger.request.info(req, registerRequest);
        AuthResponse response = authService.register(registerRequest);
        logger.response.info(req, response);
        return ResponseSuccess.of(response);
    }

    @Operation(summary = "login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @PostMapping(Api.LOGIN)
    @ResponseStatus(HttpStatus.OK)
    public ResponseSuccess<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest req) {
        logger.request.info(req, loginRequest);
        AuthResponse response = authService.login(loginRequest);
        logger.response.info(req, response);
        return ResponseSuccess.of(response);
    }

    @Operation(summary = "Refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping(Api.REFRESH)
    @ResponseStatus(HttpStatus.OK)
    public ResponseSuccess<AuthResponse> refreshToken(Authentication authentication, HttpServletRequest req) throws IOException {
        logger.request.info(req, authentication.getCredentials());
        AuthResponse response = authService.refreshToken(authentication);
        logger.response.info(req, response);
        return ResponseSuccess.of(response);
    }

    @Operation(summary = "Get current user by access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserAuthResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping(Api.ME)
    @ResponseStatus(HttpStatus.OK)
    public ResponseSuccess<UserAuthResponse> getMe(Principal principal, HttpServletRequest req) {
        logger.request.info(req, principal.getName());
        UserAuthResponse response = authService.getMe((principal));
        logger.response.info(req, response);
        return ResponseSuccess.of(response);
    }

    @Operation(summary = "Logout user by access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping(Api.LOGOUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(Principal principal, HttpServletRequest req) {
        logger.request.info(req, principal.getName());
        authService.logout(principal);
        logger.response.info(req, "Logout success");
    }
}
