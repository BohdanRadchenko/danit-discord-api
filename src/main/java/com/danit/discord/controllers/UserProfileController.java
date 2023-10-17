package com.danit.discord.controllers;

import com.danit.discord.annotations.ApiPrefix;
import com.danit.discord.constants.Api;
import com.danit.discord.dto.ResponseSuccess;
import com.danit.discord.dto.profile.ProfileResponse;
import com.danit.discord.dto.profile.UserProfileRequest;
import com.danit.discord.services.UserProfileService;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@ApiPrefix(Api.PROFILE)
@AllArgsConstructor
public class UserProfileController {
    private final Logging logger = Logging.of(UserProfileController.class);
    private final UserProfileService service;

    @Operation(summary = "Get current profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponse.class))}),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseSuccess<ProfileResponse> getAll(Principal principal, HttpServletRequest req) {
        logger.request.info(req, principal.getName());
        ProfileResponse response = service.getAll(principal);
        logger.response.info(req, response);
        return ResponseSuccess.of(response);
    }

    @Operation(summary = "Update profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = {@Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(implementation = UserProfileRequest.class))}),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content)
    })
    @PatchMapping(path = {"", "/", Api.SERVER_ID}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseSuccess<ProfileResponse> update(
    public ResponseSuccess<ProfileResponse> update(
            @PathVariable(name = Api.PARAM_SERVER_ID, required = false) String serverId,
            @Valid @ModelAttribute UserProfileRequest form,
            Principal principal,
            HttpServletRequest req
    ) {
        logger.request.info(req, String.format("%s | %s", serverId, form));
        System.out.println("form " + form);
        ProfileResponse response = service.updateProfile(form, principal, serverId);
        return ResponseSuccess.of(response);
    }
}
