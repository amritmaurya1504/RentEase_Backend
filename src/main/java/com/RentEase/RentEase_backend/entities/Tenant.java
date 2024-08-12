package com.RentEase.RentEase_backend.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tenant_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tenant extends User {
    @OneToOne(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Occupation occupation;
}
