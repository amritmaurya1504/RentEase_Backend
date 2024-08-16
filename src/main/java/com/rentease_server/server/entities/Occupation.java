package com.rentease_server.server.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "occupation_table")
public class Occupation {
    @Id
    private String occupationId;
    private String institutionName;
    private String institutionAddress;
    private String occupation;

    @OneToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
}
