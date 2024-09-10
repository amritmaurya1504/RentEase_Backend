package com.rentease_server.server.exceptions;

import com.rentease_server.server.payloads.ExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLDataException;

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

    @ExceptionHandler(RoleNotExistException.class)
    public ResponseEntity<ExceptionResponse> handlerRoleNotExistException(RoleNotExistException ex) {
        String msg = ex.getMessage();
        ExceptionResponse apiResponse = ExceptionResponse.builder()
                .message(msg)
                .success(false) // Set to false for errors
                .status(HttpStatus.NOT_FOUND) // Use HttpStatus value for the status code
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = "Data too long for field: " + ex.getMostSpecificCause().getMessage();
        ExceptionResponse apiResponse = ExceptionResponse.builder()
                .message(message)
                .success(false)
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLDataException.class)
    public ResponseEntity<ExceptionResponse> handleSQLDataException(SQLDataException ex) {
        String message = "SQL Data error: " + ex.getMessage();
        ExceptionResponse apiResponse = ExceptionResponse.builder()
                .message(message)
                .success(false)
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
