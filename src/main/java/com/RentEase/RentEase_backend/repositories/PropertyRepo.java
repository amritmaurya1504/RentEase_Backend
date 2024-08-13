package com.RentEase.RentEase_backend.repositories;

import com.RentEase.RentEase_backend.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepo extends JpaRepository<Property, String> {
    @Query("SELECT o FROM property_table o WHERE o.landlord.userId = :userId")
    List<Property> findByLandlordUserId(@Param("userId") String userId);
}
