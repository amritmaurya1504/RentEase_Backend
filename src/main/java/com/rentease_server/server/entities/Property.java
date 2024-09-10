package com.rentease_server.server.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "property_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Property {

    @Id
    private String propertyId;
    private String name;
    private String address;
    private String city;
    private String state;
    private int pinCode;
    private String tenantType;

    private String dateListed;
    private String description;
    private String floor;
    private String size;
    private String propertyAge;

    private String propertyType;
    private String availabilityStatus;

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