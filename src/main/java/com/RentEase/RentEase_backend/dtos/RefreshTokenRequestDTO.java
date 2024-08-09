package com.RentEase.RentEase_backend.dtos;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenRequestDTO {
    private String token;
}
