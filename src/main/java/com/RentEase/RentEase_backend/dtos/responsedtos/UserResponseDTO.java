package com.RentEase.RentEase_backend.dtos.responsedtos;
import com.RentEase.RentEase_backend.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private String userId;

    private String fullName;

    private String username;

    private String password;

    private String email;

    private String photoUrl;

    private Long phone;

    private String address;

    private String city;

    private String pinCode;

    private String state;

    private Role role;

    private String joinedDate;

    private boolean isUserActivated;

}
