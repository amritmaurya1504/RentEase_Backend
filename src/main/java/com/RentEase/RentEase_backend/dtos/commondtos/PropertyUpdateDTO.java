package com.RentEase.RentEase_backend.dtos.commondtos;

import com.RentEase.RentEase_backend.enums.AvailabilityStatus;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyUpdateDTO {

    @Positive(message = "Rent must be positive")
    private String rent;
    @Positive(message = "Deposit must be positive")
    private String deposit;
    private String[] photos;
    private AvailabilityStatus availabilityStatus;
}
