package com.RentEase.RentEase_backend.entities;

import com.RentEase.RentEase_backend.enums.AvailabilityStatus;
import com.RentEase.RentEase_backend.enums.PropertyType;
import com.RentEase.RentEase_backend.enums.TenantType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "property_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Property {

    @Id
    private String propertyId;
    private String address;
    private String city;
    private String state;
    private int pinCode;
    private TenantType tenantType;

    private String dateListed;
    private String description;
    private String floor;
    private String size;
    private String propertyAge;

    private PropertyType propertyType;
    private AvailabilityStatus availabilityStatus;

    private String[] photos;
    private double rent;
    private double deposit;

    private String configuration;
    private String furnishedStatus;
    private String[] furnishedAmenities;

    private String[] otherAmenities;

    @ManyToOne
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

}