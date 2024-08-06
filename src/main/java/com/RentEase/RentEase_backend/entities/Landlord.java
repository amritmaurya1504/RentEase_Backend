package com.RentEase.RentEase_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity(name = "landlord_table")
public class Landlord {
    @Id
    private String landlordId;
    private String joinedDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
