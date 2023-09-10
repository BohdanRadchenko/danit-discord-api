package com.danit.discord.responses;

import java.util.List;

public class ResponseError extends ResponseData<Object> {

    public ResponseError(String message, List<String> errors) {
        super(false, message, null, errors);
    }

    public static ResponseError of(String message) {
        return new ResponseError(message, null);
    }

    public static ResponseError of(String message, List<String> errors) {
        return new ResponseError(message, errors);
    }
}
