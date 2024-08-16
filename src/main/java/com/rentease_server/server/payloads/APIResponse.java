package com.rentease_server.server.payloads;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResponse <T>{
    private String message;
    private boolean success;
    private HttpStatus status;
    private T data;
}
