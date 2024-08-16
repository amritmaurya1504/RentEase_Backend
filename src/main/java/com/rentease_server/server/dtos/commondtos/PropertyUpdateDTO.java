package com.rentease_server.server.dtos.commondtos;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyUpdateDTO {

    @Positive(message = "Rent must be positive")
    private double rent;
    @Positive(message = "Deposit must be positive")
    private double deposit;
    private String[] photos;
    private String availabilityStatus;
}
