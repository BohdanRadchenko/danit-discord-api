package com.danit.discord.controllers;

import com.danit.discord.annotations.ApiPrefix;
import com.danit.discord.constants.Api;
import com.danit.discord.dto.ResponseSuccess;
import com.danit.discord.dto.profile.UserProfileResponse;
import com.danit.discord.services.UserProfileService;
import com.danit.discord.utils.Logging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@ApiPrefix(Api.PROFILE)
@AllArgsConstructor
public class UserProfileController {
    private final UserProfileService service;
    private final Logging logger = Logging.of(UserProfileController.class);

    @Operation(summary = "Get current profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserProfileResponse.class))}),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseSuccess<UserProfileResponse> register(Principal principal, HttpServletRequest req) {
        logger.request.info(req, principal.getName());
        UserProfileResponse response = service.get(principal);
        logger.response.info(req, response);
        return ResponseSuccess.of(response);
    }
//
//    @Operation(summary = "login")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK",
//                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))}),
//            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
//    })
//    @PostMapping(Api.LOGIN)
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseSuccess<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest req) {
//        logger.request.info(req, loginRequest);
//        AuthResponse response = authService.login(loginRequest);
//        logger.response.info(req, response);
//        return ResponseSuccess.of(response);
//    }
//
//    @Operation(summary = "Refresh token")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK",
//                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))}),
//            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
//            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
//    })
//    @GetMapping(Api.REFRESH)
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseSuccess<AuthResponse> refreshToken(Authentication authentication, HttpServletRequest req) throws IOException {
//        logger.request.info(req, authentication.getCredentials());
//        AuthResponse response = authService.refreshToken(authentication);
//        logger.response.info(req, response);
//        return ResponseSuccess.of(response);
//    }
//
//    @Operation(summary = "Get current user by access token")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK",
//                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserAuthResponse.class))}),
//            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
//    })
//    @GetMapping(Api.ME)
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseSuccess<UserAuthResponse> getMe(Principal principal, HttpServletRequest req) {
//        logger.request.info(req, principal.getName());
//        UserAuthResponse response = authService.getMe((principal));
//        logger.response.info(req, response);
//        return ResponseSuccess.of(response);
//    }
//
//    @Operation(summary = "Logout user by access token")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "No content", content = @Content),
//            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
//    })
//    @GetMapping(Api.LOGOUT)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void logout(Principal principal, HttpServletRequest req) {
//        logger.request.info(req, principal.getName());
//        authService.logout(principal);
//        logger.response.info(req, "Logout success");
//    }
}
