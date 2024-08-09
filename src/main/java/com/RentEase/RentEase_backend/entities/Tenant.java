package com.RentEase.RentEase_backend.entities;

import jakarta.persistence.*;
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

    @OneToOne(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Occupation occupation;
}
