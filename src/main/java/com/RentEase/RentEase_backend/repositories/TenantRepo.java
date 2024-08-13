package com.RentEase.RentEase_backend.repositories;

import com.RentEase.RentEase_backend.entities.Tenant;
import com.RentEase.RentEase_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepo extends JpaRepository<Tenant, String> {
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Optional<User> findByUserName(String userName);
}
