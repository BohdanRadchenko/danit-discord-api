package com.danit.discord.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NothingUpdateException extends AppException {
    public NothingUpdateException(RuntimeException exception) {
        super(exception);
    }

    public NothingUpdateException(String message) {
        super(message);
    }
}
