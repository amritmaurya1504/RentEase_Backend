package com.RentEase.RentEase_backend.exceptions;

public class UserNotAuthorizedException extends RuntimeException{
    public UserNotAuthorizedException(String message){
        super(message);
    }
}
