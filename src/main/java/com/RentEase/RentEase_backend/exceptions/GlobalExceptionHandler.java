package com.RentEase.RentEase_backend.exceptions;

import com.RentEase.RentEase_backend.payloads.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
