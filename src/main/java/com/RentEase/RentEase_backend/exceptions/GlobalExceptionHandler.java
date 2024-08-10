package com.RentEase.RentEase_backend.exceptions;

import com.RentEase.RentEase_backend.payloads.APIResponse;
import com.RentEase.RentEase_backend.payloads.ExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlerResourceNotFoundException(ResourceNotFoundException ex){
        String msg = ex.getMessage();
        ExceptionResponse apiResponse = ExceptionResponse.builder().message(msg).success(false
        ).status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        String msg = ex.getMessage();
        ExceptionResponse apiResponse = ExceptionResponse.builder()
                .message(msg)
                .success(false) // Set to false for errors
                .status(HttpStatus.CONFLICT) // Use HttpStatus value for the status code
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        ProblemDetail errorDetail = null;
        if(ex instanceof BadCredentialsException){

            // this class is provided by spring itself for error responses
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(401),
                    ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Authentication Failure !");

        }
        
        if (ex instanceof AccessDeniedException){
           errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(403), ex.getMessage());
           errorDetail.setProperty("access_denied_reason", "Not Authorized !");
        }

        if(ex instanceof SignatureException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(403), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "JWT Signature not valid !");
        }

        if(ex instanceof ExpiredJwtException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(403), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "JWT token already expired !");
        }

        return errorDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extract the default message from the validation error
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String defaultMessage = fieldError != null ? fieldError.getDefaultMessage() : "Validation error";

        ExceptionResponse apiResponse = ExceptionResponse.builder()
                .message(defaultMessage)
                .success(false)
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ExceptionResponse> handleValidUser(UserNotAuthorizedException ex){
        String msg = ex.getMessage();
        ExceptionResponse apiResponse = ExceptionResponse.builder()
                .message(msg)
                .success(false) // Set to false for errors
                .status(HttpStatus.UNAUTHORIZED) // Use HttpStatus value for the status code
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

}
