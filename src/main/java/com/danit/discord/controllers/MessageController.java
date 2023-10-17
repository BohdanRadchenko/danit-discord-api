package com.danit.discord.controllers;

import com.danit.discord.annotations.ApiPrefix;
import com.danit.discord.constants.Api;
import com.danit.discord.dto.message.MessageTypesResponse;
import com.danit.discord.responses.ResponseSuccess;
import com.danit.discord.services.MessageService;
import com.danit.discord.utils.Logging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import java.util.List;

@RestController
@ApiPrefix(Api.MESSAGES)
@AllArgsConstructor
public class MessageController {
    private final Logging logger = Logging.of(MessageController.class);
    private final MessageService messageService;

    @Operation(summary = "Get message types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MessageTypesResponse.class)))}),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content),
    })
    @GetMapping(Api.TYPES)
    @ResponseStatus(HttpStatus.OK)
    public ResponseSuccess<List<MessageTypesResponse>> getMessageTypes(HttpServletRequest req) {
        logger.request.info(req, "");
        List<MessageTypesResponse> response = messageService.getMessageTypes();
        logger.response.info(req, response);
        return ResponseSuccess.of(response);
    }
}
