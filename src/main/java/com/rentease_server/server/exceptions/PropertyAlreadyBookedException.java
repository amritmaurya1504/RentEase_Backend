package com.rentease_server.server.exceptions;

public class PropertyAlreadyBookedException extends RuntimeException{
    public PropertyAlreadyBookedException(String message){
        super(message);
    }
}
