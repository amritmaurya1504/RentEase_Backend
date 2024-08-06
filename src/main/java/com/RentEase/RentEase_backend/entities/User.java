package com.RentEase.RentEase_backend.entities;

import com.RentEase.RentEase_backend.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user_table")
public class User {

    @Id
    private String userId;
    private String fullName;
    private String userName;
    private String password;
    private String email;
    private String photoUrl;
    private Long phone;
    private String address;
    private String city;
    private int pinCode;
    private String state;
    private Role role;

}
