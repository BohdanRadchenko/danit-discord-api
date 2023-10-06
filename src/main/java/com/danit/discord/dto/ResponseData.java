package com.danit.discord.dto;

import com.danit.discord.serializers.ResponseDataSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@JsonSerialize(using = ResponseDataSerializer.class)
public class ResponseData<T> {
    private final boolean success;
    private final String message;
    private final T data;
    private final List<String> errors;

    public ResponseData(boolean success, String message, T data, List<String> errors) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errors = Objects.requireNonNullElseGet(errors, ArrayList::new);
    }
}
