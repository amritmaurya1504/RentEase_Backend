package com.RentEase.RentEase_backend.repositories;

import com.RentEase.RentEase_backend.entities.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccupationRepo extends JpaRepository<Occupation, String> {
    Occupation findByTenantTenantId(String tenantId);
}
