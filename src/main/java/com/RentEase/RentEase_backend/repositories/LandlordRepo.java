package com.RentEase.RentEase_backend.repositories;

import com.RentEase.RentEase_backend.entities.Landlord;
import com.RentEase.RentEase_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LandlordRepo extends JpaRepository<Landlord, String> {
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);

    Optional<User> findByUserName(String userName);
}
