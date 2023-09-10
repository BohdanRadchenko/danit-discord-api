package com.danit.discord.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoginException extends RuntimeException {
    public LoginException(RuntimeException exception) {
        super(exception);
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException() {
        this("Invalid email or password");
    }
}
