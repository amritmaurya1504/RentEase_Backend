package com.rentease_server.server.dtos.responsedtos;

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
