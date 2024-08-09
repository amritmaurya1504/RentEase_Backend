package com.RentEase.RentEase_backend.dtos;

import com.RentEase.RentEase_backend.entities.Tenant;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OccupationDTO {
    private String occupationId;
    @NotBlank(message = "Institution name cannot be blank")
    private String institutionName;

    @NotBlank(message = "Institution address cannot be blank")
    private String institutionAddress;

    @NotBlank(message = "Occupation cannot be blank")
    private String occupation;
    private TenantDTO tenant;
}
