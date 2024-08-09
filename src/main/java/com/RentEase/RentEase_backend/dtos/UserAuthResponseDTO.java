package com.RentEase.RentEase_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserAuthResponseDTO {
    private String message;
    private boolean success;
    private HttpStatus status;
    private UserDTO user;
    private String accessToken;
}
