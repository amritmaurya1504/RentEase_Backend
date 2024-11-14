package com.rentease_server.server.services.impl;

import com.rentease_server.server.dtos.requestdtos.EmailEnquiryDTO;
import com.rentease_server.server.entities.Property;
import com.rentease_server.server.exceptions.ResourceNotFoundException;
import com.rentease_server.server.repositories.PropertyRepo;
import com.rentease_server.server.services.EmailService;
import io.swagger.v3.oas.models.examples.Example;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private PropertyRepo propertyRepo;


    public void sendHtmlEmail(String ownerEmail, String subject,
                              Map<String, Object> templateModel) throws MessagingException {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(ownerEmail);
            helper.setSubject(subject);

            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(templateModel);
            String htmlContent = templateEngine.process("propertyEnquiry", thymeleafContext);

            helper.setText(htmlContent, true);

            javaMailSender.send(message);

    }

    @Override
    public void sendPropertyEnquiryEmail(EmailEnquiryDTO emailEnquiryDTO, String propertyId) {
        String subject = "New Property Enquiry";

        Property property = propertyRepo.findById(propertyId).orElseThrow(() -> new ResourceNotFoundException(
                "Property with given id not found: " + propertyId
        ));


        String ownerEmail = property.getLandlord().getEmail();
        String ownerName = property.getLandlord().getFullName();
        String propertyLink = "https://rent-ease-client-two.vercel.app/properties/" + propertyId;
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("ownerName", ownerName);
        templateModel.put("clientName", emailEnquiryDTO.getName());
        templateModel.put("clientEmail", emailEnquiryDTO.getEmail());
        templateModel.put("clientMessage", emailEnquiryDTO.getMsg());
        templateModel.put("propertyLink", propertyLink);

        try {
            sendHtmlEmail(ownerEmail, subject, templateModel);
        } catch (Exception e) {
            log.error("Exception while sending: ", e);
        }
    }
}
