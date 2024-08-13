package com.RentEase.RentEase_backend.exceptions;

public class RoleNotExistException extends RuntimeException{
    public RoleNotExistException(String msg) {
        super(msg);
    }
}
