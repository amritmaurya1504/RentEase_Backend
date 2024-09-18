package com.rentease_server.server.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import java.util.*;

@Entity(name = "tenant_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tenant extends User{

    @OneToOne(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Occupation occupation;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;
}
