package com.rentease_server.server.exceptions;

public class RoleNotExistException extends RuntimeException{
    public RoleNotExistException(String msg) {
        super(msg);
    }
}
