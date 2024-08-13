package com.RentEase.RentEase_backend.entities;

import jakarta.persistence.*;
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
