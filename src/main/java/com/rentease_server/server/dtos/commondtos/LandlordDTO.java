package com.rentease_server.server.dtos.commondtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LandlordDTO {

    private String userId;
    private String fullName;
    private String userName;
    private String email;
    private String photoUrl;
    private Long phone;
    private String address;
    private String city;
    private String pinCode;
    private String state;
    private String joinedDate;

}
