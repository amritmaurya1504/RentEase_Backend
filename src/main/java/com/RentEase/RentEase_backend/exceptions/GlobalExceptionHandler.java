package com.RentEase.RentEase_backend.exceptions;

import com.RentEase.RentEase_backend.payloads.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> handlerResourceNotFoundException(ResourceNotFoundException ex){
        String msg = ex.getMessage();
        APIResponse apiResponse = APIResponse.builder().message(msg).success(false
        ).status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<APIResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        String msg = ex.getMessage();
        APIResponse apiResponse = APIResponse.builder()
                .message(msg)
                .success(false) // Set to false for errors
                .status(HttpStatus.CONFLICT) // Use HttpStatus value for the status code
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleGenericException(Exception ex) {
        APIResponse apiResponse = APIResponse.builder()
                .message(ex.getMessage())
                .success(false) // Set to false for errors
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // Use HttpStatus value for the status code
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extract the default message from the validation error
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String defaultMessage = fieldError != null ? fieldError.getDefaultMessage() : "Validation error";

        APIResponse apiResponse = APIResponse.builder()
                .message(defaultMessage)
                .success(false)
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<APIResponse> handleValidUser(UserNotAuthorizedException ex){
        String msg = ex.getMessage();
        APIResponse apiResponse = APIResponse.builder()
                .message(msg)
                .success(false) // Set to false for errors
                .status(HttpStatus.UNAUTHORIZED) // Use HttpStatus value for the status code
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

}
