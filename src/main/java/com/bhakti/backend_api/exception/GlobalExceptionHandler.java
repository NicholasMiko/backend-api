package com.bhakti.backend_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ErrorResponse>
    handleValidationException(
            MethodArgumentNotValidException ex
    ) {

        List<String> errors =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error ->
                                error.getDefaultMessage()
                        )
                        .toList();

        ErrorResponse response =
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation Failed",
                        errors
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(
            IllegalArgumentException.class
    )
    public ResponseEntity<ErrorResponse>
    handleIllegalArgumentException(
            IllegalArgumentException ex
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Invalid Request",
                        List.of(
                                ex.getMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
    @ExceptionHandler(
        ResourceNotFoundException.class
)
public ResponseEntity<ErrorResponse>
handleResourceNotFoundException(
        ResourceNotFoundException ex
) {

    ErrorResponse response =
            new ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Resource Not Found",
                    List.of(
                            ex.getMessage()
                    )
            );

    return ResponseEntity
            .status(
                    HttpStatus.NOT_FOUND
            )
            .body(response);
}
}