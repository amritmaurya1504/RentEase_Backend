package com.rentease_server.server.controllers;

import com.rentease_server.server.dtos.requestdtos.EmailEnquiryDTO;
import com.rentease_server.server.payloads.APIResponse;
import com.rentease_server.server.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-enquiry/{propertyId}")
    public APIResponse<Void> sendPropertyEnquiry(@PathVariable String propertyId,
                                                 @RequestBody EmailEnquiryDTO emailEnquiryDTO){
        emailService.sendPropertyEnquiryEmail(emailEnquiryDTO, propertyId);
        return new APIResponse<>("Message Sent Successfully!", true, HttpStatus.OK, null);
    }

}
