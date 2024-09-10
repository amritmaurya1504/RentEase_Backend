package com.rentease_server.server.dtos.requestdtos;


import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PropertyReqDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "State cannot be blank")
    private String state;

    @NotBlank(message = "Pin code cannot be blank")
    @Pattern(regexp = "\\d{6}", message = "Pin code must be 6 digits")
    private String pinCode;

    @NotNull(message = "Tenant type cannot be null")
    private String tenantType;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotBlank(message = "Floor cannot be blank")
    private String floor;

    @NotBlank(message = "Size cannot be blank")
    private String size;

    @NotBlank(message = "Property age cannot be blank")
    private String propertyAge;

    @NotNull(message = "Property type cannot be null")
    private String propertyType;

    @NotNull(message = "Availability status cannot be null")
    private String availabilityStatus;

    @NotNull(message = "Photos cannot be null")
    @Size(min = 1, message = "There must be at least one photo")
    private String[] photos;

    @Positive(message = "Rent must be positive")
    private double rent;

    @Positive(message = "Deposit must be positive")
    private double deposit;

    @NotBlank(message = "Configuration cannot be blank")
    private String configuration;

    @NotBlank(message = "Furnished status cannot be blank")
    private String furnishedStatus;

    @NotNull(message = "Furnished amenities cannot be null")
    private String[] furnishedAmenities;

    private String[] otherAmenities;

}
