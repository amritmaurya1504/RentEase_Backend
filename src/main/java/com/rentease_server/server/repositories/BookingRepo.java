package com.rentease_server.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentease_server.server.entities.Booking;

public interface BookingRepo extends JpaRepository<Booking, String>{
    
}
