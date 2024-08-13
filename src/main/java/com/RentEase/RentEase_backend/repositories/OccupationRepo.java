package com.RentEase.RentEase_backend.repositories;

import com.RentEase.RentEase_backend.entities.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OccupationRepo extends JpaRepository<Occupation, String> {

    // Custom query to find Occupation by Tenant's user ID
    @Query("SELECT o FROM occupation_table o WHERE o.tenant.userId = :userId")
    Occupation findByTenantUserId(@Param("userId") String userId);
}
