package com.danit.discord.controllers;

import com.danit.discord.annotations.ApiPrefix;
import com.danit.discord.constants.Api;
import com.danit.discord.dto.server.ServerResponse;
import com.danit.discord.dto.user.UserInviteRequest;
import com.danit.discord.dto.user.UserResponse;

import com.danit.discord.entities.Invite;
import com.danit.discord.enums.InviteType;
import com.danit.discord.responses.ResponseSuccess;
import com.danit.discord.services.InviteService;
import com.danit.discord.services.UserService;
import com.danit.discord.utils.Logging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import static com.danit.discord.enums.StatusType.*;

@RestController
@ApiPrefix(Api.USERS)
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final InviteService inviteService;
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
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseSuccess.class))}),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })

    @PostMapping(Api.INVITES)
    @ResponseStatus(HttpStatus.OK)
    public void inviteToFriend(
            Principal principal,
            @RequestBody @Valid UserInviteRequest request,
            HttpServletRequest req) {
        String msg = String.format("body: %s",  request);
        logger.request.info(req, msg);
        inviteService.created(principal.getName(), request, InviteType.FRIENDS);
    }
    @Operation(summary = "Accepted invite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseSuccess.class))}),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PostMapping(Api.INVITE_ACCEPTED)
    @ResponseStatus(HttpStatus.OK)
    public void AcceptedAddToFriend(
            Principal principal,
            @RequestBody @Valid UserInviteRequest request,
            HttpServletRequest req) {
        String msg = String.format("body: %s",  request);
        logger.request.info(req, msg);
        inviteService.inviteAccepted(ACCEPTED, request.getId());
    }
    @Operation(summary = "Rejected invite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseSuccess.class))}),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PostMapping(Api.INVITE_REJECTED)
    @ResponseStatus(HttpStatus.OK)
    public void RejectedAddToFriend(
            Principal principal,
            @RequestBody @Valid UserInviteRequest request,
            HttpServletRequest req) {
        String msg = String.format("body: %s",  request);
        logger.request.info(req, msg);
        inviteService.inviteRejected(REJECT, request.getId());
    }
    @Operation(summary = "Remove user from friend")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseSuccess.class))}),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PostMapping(Api.REMOVE)
    @ResponseStatus(HttpStatus.OK)
    public void removeFriend(
            Principal principal,
            @RequestBody @Valid UserInviteRequest request,
            HttpServletRequest req) {
        String msg = String.format("body: %s",  request);
        logger.request.info(req, msg);
        userService.remove(principal.getName(), request);
    }
    @Operation(summary = "Get all invites to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Invite.class)))}),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping(Api.INVITES)
    @ResponseStatus(HttpStatus.OK)
    public List<Invite> getAllInvite(
            @RequestBody @Valid UserInviteRequest request,
            HttpServletRequest req) {
        String msg = String.format("body: %s",  request);
        logger.request.info(req, msg);
        return inviteService.getAllInviteToId(request.getId());
    }
}
