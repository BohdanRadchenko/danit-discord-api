package com.danit.discord.controllers;

import com.danit.discord.annotations.ApiPrefix;
import com.danit.discord.constants.Api;
import com.danit.discord.dto.auth.AuthResponse;
import com.danit.discord.dto.auth.LoginRequest;
import com.danit.discord.dto.auth.RegisterRequest;
import com.danit.discord.dto.user.UserResponse;
import com.danit.discord.responses.ResponseSuccess;
import com.danit.discord.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@ApiPrefix(Api.AUTH)
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Registration user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @PostMapping(Api.REGISTER)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseSuccess<AuthResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseSuccess.of(authService.register(registerRequest));
    }

    @Operation(summary = "login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @PostMapping(Api.LOGIN)
    @ResponseStatus(HttpStatus.OK)
    public ResponseSuccess<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseSuccess.of(authService.login(loginRequest));
    }

    @Operation(summary = "get me")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping(Api.ME)
    @ResponseStatus(HttpStatus.OK)
//    public ResponseSuccess<AuthResponse> getMe(Principal principal) {
    public void getMe(Principal principal) {
//    public void getMe() {
        System.out.println("principal " + principal);
//        return ResponseSuccess.of(authService.login(loginRequest));
    }

    @GetMapping(Api.REFRESH)
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
    }
}
