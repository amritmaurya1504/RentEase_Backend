package com.RentEase.RentEase_backend.dtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDTO {
    @Size(min = 1, max = 100, message = "Full name must be between 1 and 100 characters")
    private String fullName;
    private String photoUrl;
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;
    private String address;
    private String city;
    @Pattern(regexp = "\\d{6}", message = "Pin code must be 6 digits")
    private String pinCode;
    private String state;
}
