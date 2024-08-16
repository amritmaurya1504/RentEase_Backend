package com.rentease_server.server.dtos.requestdtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OccupationReqDTO {
    @NotBlank(message = "Institution name cannot be blank")
    private String institutionName;

    @NotBlank(message = "Institution address cannot be blank")
    private String institutionAddress;

    @NotBlank(message = "Occupation cannot be blank")
    private String occupation;

}
