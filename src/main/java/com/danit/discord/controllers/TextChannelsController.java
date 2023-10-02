package com.danit.discord.controllers;

import com.danit.discord.annotations.ApiPrefix;
import com.danit.discord.constants.Api;
import com.danit.discord.dto.channel.ChannelInviteRequest;
import com.danit.discord.dto.text.TextChannelResponse;
import com.danit.discord.entities.TextChannel;
import com.danit.discord.responses.ResponseSuccess;
import com.danit.discord.services.TextChannelService;
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
@ApiPrefix(Api.CHANNELS_TEXT)
@AllArgsConstructor
public class TextChannelsController {
    private final Logging logger = Logging.of(TextChannelsController.class);
    private final TextChannelService textChannelService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseSuccess<List<TextChannelResponse>> getAll() {
        List<TextChannelResponse> response = textChannelService.get();
        return ResponseSuccess.of(response);
    }

    @Operation(summary = "Get text channel by link")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TextChannelResponse.class))}),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping(Api.LINK)
    @ResponseStatus(HttpStatus.OK)
    public ResponseSuccess<TextChannelResponse> getTextChannelByLink(@PathVariable(Api.PARAM_LINK) String link, Principal principal, HttpServletRequest req) {
        logger.request.info(req, link);
        TextChannelResponse response = textChannelService.getByLinkResponse(link, principal.getName());
        logger.response.info(req, response);
        return ResponseSuccess.of(response);
    }

    @Operation(summary = "Invite user to text channel")
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
    public ResponseSuccess<?> inviteToChannel(
            @PathVariable(Api.PARAM_LINK) String link,
            Principal principal,
            @RequestBody @Valid ChannelInviteRequest request,
            HttpServletRequest req) {
        String msg = String.format("link: %s, body: %s", link, request);
        logger.request.info(req, msg);
        // TODO: invite to server if user not invited
        TextChannel channel = textChannelService.invite(link, principal.getName(), request);
        logger.response.info(req, msg);
        return ResponseSuccess.of(String.format("User added to '%s' channel", channel.getTitle()));
    }
}
