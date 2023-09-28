package com.danit.discord.controllers;

import com.danit.discord.exceptions.AlreadyExistException;
import com.danit.discord.exceptions.AppException;
import com.danit.discord.exceptions.ForbiddenException;
import com.danit.discord.exceptions.NotFoundException;
import com.danit.discord.responses.ResponseError;
import com.danit.discord.utils.Logging;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionRestControllerAdvice {
    private final Logging logger = Logging.of(ExceptionRestControllerAdvice.class);

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
    public ResponseEntity<ResponseError> notFound(NotFoundException x, HttpServletRequest req) {
        logger.response.error(req, x.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseError.of(x.getMessage()));
    }

    @ExceptionHandler({AlreadyExistException.class})
    public ResponseEntity<ResponseError> alreadyExist(AlreadyExistException x, HttpServletRequest req) {
        logger.response.error(req, x.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ResponseError.of(x.getMessage()));
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<ResponseError> alreadyExist(ForbiddenException x, HttpServletRequest req) {
        logger.response.error(req, x.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ResponseError.of(x.getMessage()));
    }

    @ExceptionHandler({AppException.class})
    public ResponseEntity<ResponseError> appException(AppException x, HttpServletRequest req) {
        logger.response.error(req, x.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseError.of(x.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseError> appException(Exception x) {
        System.out.println(x);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseError.of(x.getMessage()));
    }
}
