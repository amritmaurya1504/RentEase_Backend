package com.RentEase.RentEase_backend.repositories;

import com.RentEase.RentEase_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
