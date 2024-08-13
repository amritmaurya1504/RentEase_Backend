package com.RentEase.RentEase_backend.dtos.responsedtos;

import com.RentEase.RentEase_backend.dtos.responsedtos.UserResponseDTO;
import com.RentEase.RentEase_backend.entities.Tenant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OccupationResDTO {
    private String occupationId;
    private String institutionName;
    private String institutionAddress;
    private String occupation;
}
