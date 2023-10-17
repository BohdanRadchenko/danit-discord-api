package com.danit.discord.responses;

public class ResponseSuccess<T> extends ResponseData<T> {

    public ResponseSuccess(String message, T data) {
        super(true, message, data, null);
    }

    public static <T> ResponseSuccess<T> of(String message, T data) {
        return new ResponseSuccess<>(message, data);
    }

    public static <T> ResponseSuccess<T> of(T data) {
        return new ResponseSuccess<>(null, data);
    }

    public static ResponseSuccess<?> of(String message) {
        return new ResponseSuccess<>(message, null);
    }
}
