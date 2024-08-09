package com.RentEase.RentEase_backend.repositories;

import com.RentEase.RentEase_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    User findByTenantTenantId(String tenantId);
    Optional<User> findByUserName(String userName);
}
