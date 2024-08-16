package com.rentease_server.server.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "landlord_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Landlord extends User{

    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Property> properties = new ArrayList<>();

}
