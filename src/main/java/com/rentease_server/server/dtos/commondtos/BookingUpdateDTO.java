package com.rentease_server.server.dtos.commondtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BookingUpdateDTO {
    private String bookingStatus;
    private String cancelationDate;
    private String isApprovedByLandlord;
}
