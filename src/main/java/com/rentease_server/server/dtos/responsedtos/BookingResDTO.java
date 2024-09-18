package com.rentease_server.server.dtos.responsedtos;

import com.rentease_server.server.dtos.commondtos.PaymentDTO;
import com.rentease_server.server.dtos.commondtos.TenantDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingResDTO {
    private String bookingId;
    private String bookingDate;
    private String bookingFrom;
    private String bookingTo;
    private String bookingStatus;
    private double deposit;
    private double rent;
    private String cancelationDate;
    private String isApprovedByLandlord;
    private TenantDTO tenant;
    private PropertyResDTO property;
    private List<PaymentDTO> payments;
}
