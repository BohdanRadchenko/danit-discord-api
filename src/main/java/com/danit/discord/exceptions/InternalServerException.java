package com.danit.discord.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends AppException {
    public InternalServerException(RuntimeException exception) {
        super(exception);
    }

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException() {
        super("INTERNAL SERVER EXCEPTION");
    }
}
