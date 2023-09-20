package com.danit.discord.controllers;

import com.danit.discord.annotations.ApiPrefix;
import com.danit.discord.constants.Api;
import com.danit.discord.dto.server.ServerRequest;
import com.danit.discord.dto.server.ServerResponse;
import com.danit.discord.responses.ResponseSuccess;
import com.danit.discord.services.ServerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseSuccess<ServerResponse> create(@RequestBody ServerRequest serverRequest) {
        return ResponseSuccess.of(serverService.create(serverRequest));
    }

}

