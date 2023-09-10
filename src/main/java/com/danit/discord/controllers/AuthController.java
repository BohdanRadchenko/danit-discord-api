package com.danit.discord.controllers;

import com.danit.discord.annotations.ApiPrefix;
import com.danit.discord.constants.Api;
import com.danit.discord.dto.auth.AuthResponse;
import com.danit.discord.dto.auth.LoginRequest;
import com.danit.discord.dto.auth.RegisterRequest;
import com.danit.discord.responses.ResponseSuccess;
import com.danit.discord.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseSuccess<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseSuccess.of(authService.register(registerRequest));
    }

    @PostMapping(Api.LOGIN)
    public ResponseSuccess<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseSuccess.of(authService.login(loginRequest));
    }

//    @PostMapping("/refresh")
//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        service.refreshToken(request, response);
//    }
}
