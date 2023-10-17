package com.danit.discord.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlreadyExistException extends AppException {
    public AlreadyExistException(RuntimeException exception) {
        super(exception);
    }

    public AlreadyExistException(String message) {
        super(message);
    }
}
