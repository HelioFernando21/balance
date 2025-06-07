package com.bank.balance.config;
import com.bank.balance.exception.BadRequestException;
import com.bank.balance.exception.GlobalErrorResponse;
import com.bank.balance.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        GlobalErrorResponse error = new GlobalErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );
        System.out.printf("System error %s%n", error);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalErrorResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        GlobalErrorResponse error = new GlobalErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found Error",
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );
        System.out.printf("System error %s%n", error);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GlobalErrorResponse> handleBadRequestException(BadRequestException ex, WebRequest request) {
        GlobalErrorResponse error = new GlobalErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request Error",
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );
        System.out.printf("System error %s%n", error);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
