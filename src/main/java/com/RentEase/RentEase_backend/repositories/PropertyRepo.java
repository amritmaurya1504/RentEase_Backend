package com.RentEase.RentEase_backend.repositories;

import com.RentEase.RentEase_backend.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepo extends JpaRepository<Property, String> {
    // Method to find all properties by landlord ID
    List<Property> findByLandlordLandlordId(String landlordId);
}
