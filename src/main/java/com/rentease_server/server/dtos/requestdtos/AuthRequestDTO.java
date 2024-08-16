package com.rentease_server.server.dtos.requestdtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO {
    @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters")
    private String userName;
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;
}
