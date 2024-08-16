package com.rentease_server.server.repositories;

import com.rentease_server.server.entities.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LandlordRepo extends JpaRepository<Landlord, String> {
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);

    Optional<Landlord> findByUserName(String userName);
}
