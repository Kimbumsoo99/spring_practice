package com.example.oauthjwt.exception;

import com.example.oauthjwt.dto.ApiError;
import com.example.oauthjwt.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        ApiError apiError = new ApiError("EMAIL_ALREADY_EXISTS", ex.getMessage());
        ApiResponse<Void> response = new ApiResponse<>(
                "error",
                null,
                "Email already exists",
                apiError
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidInputException(InvalidInputException ex) {
        System.out.println("GlobalExceptionHandler.handleInvalidInputException");
        ex.printStackTrace();

        ApiError apiError = new ApiError("INVALID_INPUT", ex.getMessage());
        ApiResponse<Void> response = new ApiResponse<>(
                "error",
                null,
                "Invalid input",
                apiError
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        ApiError apiError = new ApiError("INTERNAL_SERVER_ERROR", ex.getMessage());
        ApiResponse<Void> response = new ApiResponse<>(
                "error",
                null,
                "An unexpected error occurred",
                apiError
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
