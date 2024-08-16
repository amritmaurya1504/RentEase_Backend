package com.rentease_server.server.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity(name = "tenant_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tenant extends User{

    @OneToOne(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Occupation occupation;

}
