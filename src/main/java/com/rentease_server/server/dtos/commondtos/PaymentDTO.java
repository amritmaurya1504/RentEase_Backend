package com.rentease_server.server.dtos.commondtos;

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
public class PaymentDTO {
    private String paymentId;              
    private double amount;                 
    private String paymentType;            
    private String paymentDate;     
    private String paymentStatus; 
}
