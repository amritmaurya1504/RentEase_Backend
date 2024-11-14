package com.rentease_server.server.services;

//import com.rentease_server.server.dtos.requestdtos.EmailEnquiryDTO;

import com.rentease_server.server.dtos.requestdtos.EmailEnquiryDTO;

public interface EmailService {
    void sendPropertyEnquiryEmail(EmailEnquiryDTO emailEnquiryDTO, String propertyId);
}
