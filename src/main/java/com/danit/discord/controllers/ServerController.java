package com.danit.discord.controllers;

import com.danit.discord.annotations.ApiPrefix;
import com.danit.discord.constants.Api;
import com.danit.discord.dto.ResponseSuccess;
import com.danit.discord.dto.server.ServerRequest;
import com.danit.discord.dto.server.ServerResponse;
import com.danit.discord.services.ServerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@ApiPrefix(Api.SERVERS)
@RequiredArgsConstructor
public class ServerController {

    private final ServerService serverService;

    @Operation(summary = "Server create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ServerResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseSuccess<ServerResponse> create(@RequestBody ServerRequest serverRequest, Principal principal) {
        return ResponseSuccess.of(serverService.create(serverRequest, principal));
    }

    @Operation(summary = "Get servers for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ServerResponse.class)))})
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseSuccess<List<ServerResponse>> getAll(Principal principal) {
        return ResponseSuccess.of(serverService.getByUserEmail(principal.getName()));
    }

}

