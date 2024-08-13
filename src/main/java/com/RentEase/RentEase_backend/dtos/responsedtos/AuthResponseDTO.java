package com.RentEase.RentEase_backend.dtos.responsedtos;

import com.RentEase.RentEase_backend.dtos.responsedtos.UserResponseDTO;
import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class AuthResponseDTO {
    private String message;
    private boolean success;
    private HttpStatus status;
    private UserResponseDTO user;
    private String accessToken;
    private String refreshToken;
}
