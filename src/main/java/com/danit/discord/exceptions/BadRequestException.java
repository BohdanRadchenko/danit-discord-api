package com.danit.discord.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends AppException {
    public BadRequestException(RuntimeException exception) {
        super(exception);
    }

    public BadRequestException(String message) {
        super(message);
    }
}
