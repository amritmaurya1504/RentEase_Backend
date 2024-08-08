package com.RentEase.RentEase_backend.dtos;

import com.RentEase.RentEase_backend.enums.AvailabilityStatus;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyUpdateDTO {

    private double rent;
    private double rentPerSquareFt;
    private double deposit;
    private String configuration;
    private String[] photos;
    private AvailabilityStatus availabilityStatus;
}
