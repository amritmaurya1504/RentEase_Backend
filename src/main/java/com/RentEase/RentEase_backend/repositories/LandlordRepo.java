package com.RentEase.RentEase_backend.repositories;

import com.RentEase.RentEase_backend.entities.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandlordRepo extends JpaRepository<Landlord, String> {
}
