package com.RentEase.RentEase_backend.dtos;

import com.RentEase.RentEase_backend.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String userId;

    @NotBlank(message = "Full name cannot be empty")
    @Size(min = 1, max = 100, message = "Full name must be between 1 and 100 characters")
    private String fullName;

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters")
    private String userName;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    private String photoUrl;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    @NotBlank(message = "City cannot be empty")
    private String city;

    @NotNull(message = "Pin code cannot be null")
    @Pattern(regexp = "\\d{6}", message = "Pin code must be 6 digits")
    private String pinCode;

    @NotBlank(message = "State cannot be empty")
    private String state;

    @NotNull(message = "Role cannot be null")
    private Role role;
}
