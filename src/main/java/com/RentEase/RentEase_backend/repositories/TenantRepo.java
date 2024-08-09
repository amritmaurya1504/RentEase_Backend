package com.RentEase.RentEase_backend.repositories;

import com.RentEase.RentEase_backend.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepo extends JpaRepository<Tenant, String> {
}
