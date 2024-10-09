package com.rentease_server.server.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "payment_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    private String paymentId;              

    private double amount;                 
    private String paymentType;            
    private String paymentDate;     
    private String paymentStatus;          

    // @ManyToOne
    // @JoinColumn(name = "booking_id")
    // private Booking booking;             
}

