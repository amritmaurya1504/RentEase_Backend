package com.rentease_server.server.repositories;

import com.rentease_server.server.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepo extends JpaRepository<Tenant, String> {
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Optional<Tenant> findByUserName(String userName);
}
