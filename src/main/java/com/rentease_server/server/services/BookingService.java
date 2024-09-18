package com.rentease_server.server.services;

import com.rentease_server.server.dtos.commondtos.BookingUpdateDTO;
import com.rentease_server.server.dtos.requestdtos.BookingReqDTO;
import com.rentease_server.server.dtos.responsedtos.BookingResDTO;
import java.util.*;

public interface BookingService {
    
    BookingResDTO createBooking(BookingReqDTO bookingReqDTO);
    BookingResDTO getSingleBooking(String bookingId);
    List<BookingResDTO> getBookingsOfProperty(String propertyId);
    List<BookingResDTO> getBookingsOfLandlord(String landlordId);
    List<BookingResDTO> getBookingsOfTenant(String tenantId);
    void cancleBooking(String bookingId);
    BookingResDTO updateBooking(String bookingId, BookingUpdateDTO bookingUpdateDTO);
}
