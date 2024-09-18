package com.rentease_server.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentease_server.server.entities.Payment;

public interface PaymentRepo extends JpaRepository<Payment, String>{
    
}
