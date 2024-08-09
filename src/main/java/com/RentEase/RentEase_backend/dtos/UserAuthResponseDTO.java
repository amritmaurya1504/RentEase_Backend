package com.RentEase.RentEase_backend.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserAuthResponseDTO {
    private String message;
    private boolean success;
    private HttpStatus status;
    private UserDTO user;
    private String accessToken;
    private String refreshToken;
}
