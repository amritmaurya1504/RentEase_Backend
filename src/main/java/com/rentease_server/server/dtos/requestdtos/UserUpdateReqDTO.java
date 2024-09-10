package com.rentease_server.server.dtos.requestdtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateReqDTO {
    @Size(min = 1, max = 100, message = "Full name must be between 1 and 100 characters")
    private String fullName;
    private String photoUrl;
    @Min(value = 1000000000, message = "Phone number must be exactly 10 digits")
    @Max(value = 9999999999L, message = "Phone number must be exactly 10 digits")
    private Long phone;
    private String dob;
    private String address;
    private String city;

    @Min(value = 100000, message = "Pin code must be exactly 6 digits")
    @Max(value = 999999, message = "Pin code must be exactly 6 digits")
    private String pinCode;
    private String state;
}

