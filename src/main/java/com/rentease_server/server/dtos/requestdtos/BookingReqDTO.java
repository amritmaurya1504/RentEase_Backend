package com.rentease_server.server.dtos.requestdtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingReqDTO {
    @NotBlank(message = "Booking from date cannot be blank")
    private String bookingFrom;
    @NotBlank(message = "Booking to date cannot be blank")
    private String bookingTo;
    private double deposit;
    private double rent;
}
