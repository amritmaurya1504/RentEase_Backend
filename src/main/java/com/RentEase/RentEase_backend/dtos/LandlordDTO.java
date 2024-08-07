package com.RentEase.RentEase_backend.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LandlordDTO {
    private String landlordId;
    private String joinedDate;
}
