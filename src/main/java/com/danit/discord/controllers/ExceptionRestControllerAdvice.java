package com.danit.discord.controllers;

import com.danit.discord.exceptions.AlreadyExistException;
import com.danit.discord.exceptions.AppException;
import com.danit.discord.exceptions.NotFoundException;
import com.danit.discord.responses.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionRestControllerAdvice {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionRestControllerAdvice.class);

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseError> handleValidationErrors(MethodArgumentNotValidException x) {
        List<String> errors = x
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        errors.forEach(System.out::println);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseError.of(x.getBody().getDetail(), errors));
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ResponseError> notFound(NotFoundException x) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseError.of(x.getMessage()));
    }

    @ExceptionHandler({AlreadyExistException.class})
    public ResponseEntity<ResponseError> alreadyExist(AlreadyExistException x) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ResponseError.of(x.getMessage()));
    }

    @ExceptionHandler({AppException.class})
    public ResponseEntity<ResponseError> appException(AppException x) {
        System.out.println(x);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseError.of(x.getMessage()));
    }
}
