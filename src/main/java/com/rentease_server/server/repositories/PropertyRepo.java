package com.rentease_server.server.repositories;

import com.rentease_server.server.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepo extends JpaRepository<Property, String> {
    @Query("SELECT o FROM property_table o WHERE o.landlord.userId = :userId")
    List<Property> findByLandlordUserId(@Param("userId") String userId);

    List<Property> findByRentBetween(double minRent, double maxRent);

    List<Property> findByPropertyTypeIn(List<String> propertyTypes);

    List<Property> findByConfigurationIn(List<String> configurations);

    List<Property> findByFurnishedStatusIn(List<String> furnishedStatuses);
}
