package com.danit.discord.controllers;

import com.danit.discord.annotations.ApiPrefix;
import com.danit.discord.constants.Api;
import com.danit.discord.dto.channel.ChannelInviteRequest;
import com.danit.discord.dto.user.UserInviteRequest;
import com.danit.discord.dto.user.UserResponse;

import com.danit.discord.entities.User;
import com.danit.discord.responses.ResponseSuccess;
import com.danit.discord.services.UserService;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@ApiPrefix(Api.USERS)
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final Logging logger = Logging.of(UserController.class);

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseSuccess<List<UserResponse>> getAllUsers() {
        return ResponseSuccess.of(userService.getAllUsers());
    }
    @Operation(summary = "Invite user to friend")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "NO CONTENT",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseSuccess.class))}),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PostMapping(Api.LINK_INVITE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseSuccess<?> inviteToFriend(
            @PathVariable(Api.PARAM_LINK) String link,
            Principal principal,
            @RequestBody @Valid UserInviteRequest request,
            HttpServletRequest req) {
        String msg = String.format("link: %s, body: %s", link, request);
        logger.request.info(req, msg);
        User user = userService.invite(true, principal.getName(), request);
        logger.response.info(req, msg);
        return ResponseSuccess.of(String.format("User %s added to your friend", user));
    }
}
