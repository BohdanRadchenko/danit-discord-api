package com.danit.discord.dto.message;

import com.danit.discord.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageTypesResponse {
    private int id;
    private String type;

    public static MessageTypesResponse of(MessageType mt) {
        return MessageTypesResponse
                .builder()
                .id(mt.ordinal())
                .type(mt.name())
                .build();
    }

    public static List<MessageTypesResponse> getAllTypes() {
        return Arrays
                .stream(MessageType.values())
                .map(MessageTypesResponse::of)
                .collect(Collectors.toList());
    }
}
