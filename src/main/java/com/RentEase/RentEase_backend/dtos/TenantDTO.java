package com.RentEase.RentEase_backend.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantDTO {
    private String tenantId;
    private String joinedDate;
}
