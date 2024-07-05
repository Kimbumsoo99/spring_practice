package com.ssafy.memberPjt.exception;

import com.ssafy.memberPjt.dto.ApiError;
import com.ssafy.memberPjt.dto.ApiResponse;
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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        System.out.println("GlobalExceptionHandler.handleAccessDeniedException");
        ex.printStackTrace();

        ApiError apiError = new ApiError("ACCESS_DENIED", ex.getMessage());
        ApiResponse<Void> response = new ApiResponse<>(
                "error",
                null,
                "Access denied",
                apiError
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
