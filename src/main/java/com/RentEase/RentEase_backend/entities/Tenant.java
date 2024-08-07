package com.RentEase.RentEase_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity(name = "tenant_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tenant {
    @Id
    private String tenantId;
    private String joinedDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
