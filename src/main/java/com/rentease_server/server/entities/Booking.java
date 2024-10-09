package com.rentease_server.server.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Entity(name = "booking_table")
public class Booking {
    @Id
    private String bookingId;
    private String bookingDate;
    private String bookingFrom;
    private String bookingTo;
    private String bookingStatus;
    private double deposit;
    private double rent;
    private String cancelationDate;
    private boolean isApprovedByLandlord;

    @ManyToOne
    @JoinColumn(name = "tenand_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

}
