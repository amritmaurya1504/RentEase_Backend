package com.rentease_server.server.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.time.*;
import org.hibernate.grammars.hql.HqlParser.LocalDateTimeContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.rentease_server.server.dtos.commondtos.BookingUpdateDTO;
import com.rentease_server.server.dtos.requestdtos.BookingReqDTO;
import com.rentease_server.server.dtos.responsedtos.BookingResDTO;
import com.rentease_server.server.entities.Booking;
import com.rentease_server.server.entities.Payment;
import com.rentease_server.server.entities.Property;
import com.rentease_server.server.entities.Tenant;
import com.rentease_server.server.exceptions.PropertyAlreadyBookedException;
import com.rentease_server.server.exceptions.ResourceNotFoundException;
import com.rentease_server.server.repositories.BookingRepo;
import com.rentease_server.server.repositories.LandlordRepo;
import com.rentease_server.server.repositories.PropertyRepo;
import com.rentease_server.server.repositories.TenantRepo;
import com.rentease_server.server.services.BookingService;

public class BookingServiceImpl implements BookingService {

    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private PropertyRepo propertyRepo;

    @Autowired
    private LandlordRepo landlordRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookingRepo bookingRepo;

    @Override
    public BookingResDTO createBooking(BookingReqDTO bookingReqDTO, String tenantId, String propertyId) {

        // 1. Check if tenant and property exists
        Tenant tenant = tenantRepo.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));

        Property property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
                
        // 2. Check property availability
        if(property.getAvailabilityStatus().equals("Booked")){
            throw new PropertyAlreadyBookedException("Property is already booked!");
        }

        //3. Check rent/deposit
        if(property.getRent() != bookingReqDTO.getRent() || property.getDeposit() != bookingReqDTO.getDeposit()){
            throw new ResourceNotFoundException("Data Mismatched!");
        }

        //4. Map data to entity
        Booking booking = Booking.builder()
        .bookingId(UUID.randomUUID().toString())
        .bookingDate(LocalDateTime.now().toString())
        .bookingFrom(bookingReqDTO.getBookingFrom())
        .bookingTo(bookingReqDTO.getBookingTo())
        .bookingStatus("Pending")
        .rent(property.getRent())
        .deposit(property.getDeposit())
        .tenant(tenant)
        .property(property)
        .isApprovedByLandlord(false)
        .cancelationDate("")
        .build();

        //5. Save data to DB
        return this.modelMapper.map(bookingRepo.save(booking), BookingResDTO.class);
    }

    @Override
    public BookingResDTO getSingleBooking(String bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        return this.modelMapper.map(booking, BookingResDTO.class);
    }

    @Override
    public List<BookingResDTO> getBookingsOfProperty(String propertyId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookingsOfProperty'");
    }

    @Override
    public List<BookingResDTO> getBookingsOfLandlord(String landlordId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookingsOfLandlord'");
    }

    @Override
    public List<BookingResDTO> getBookingsOfTenant(String tenantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookingsOfTenant'");
    }

    @Override
    public void cancleBooking(String bookingId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancleBooking'");
    }

    @Override
    public BookingResDTO updateBooking(String bookingId, BookingUpdateDTO bookingUpdateDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBooking'");
    }

}
