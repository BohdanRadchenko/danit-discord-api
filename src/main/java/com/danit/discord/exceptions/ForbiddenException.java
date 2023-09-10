package com.danit.discord.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(RuntimeException exception) {
        super(exception);
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
