package com.RentEase.RentEase_backend.services.impl;

import com.RentEase.RentEase_backend.dtos.commondtos.PropertyDTO;
import com.RentEase.RentEase_backend.dtos.commondtos.PropertyUpdateDTO;
import com.RentEase.RentEase_backend.entities.Landlord;
import com.RentEase.RentEase_backend.entities.Property;
import com.RentEase.RentEase_backend.enums.Role;
import com.RentEase.RentEase_backend.exceptions.ResourceNotFoundException;
import com.RentEase.RentEase_backend.exceptions.UserNotAuthorizedException;
import com.RentEase.RentEase_backend.repositories.LandlordRepo;
import com.RentEase.RentEase_backend.repositories.PropertyRepo;
import com.RentEase.RentEase_backend.services.PropertyService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PropertyServiceImpl implements PropertyService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyServiceImpl.class);

    @Autowired
    private PropertyRepo propertyRepo;


    @Autowired
    private LandlordRepo landlordRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PropertyDTO createProperty(PropertyDTO propertyDTO, String landlordId) {

        //1. Check landlord exist or not
        Landlord landlord = landlordRepo.findById(landlordId).orElseThrow(
                () -> new ResourceNotFoundException
                        ("Landlord doesn't exist with id: " + landlordId)
        );

        //2. Convert DTO to Entity
        Property property = this.modelMapper.map(propertyDTO, Property.class);
        //3. Add Landlord to property
        property.setPropertyId(UUID.randomUUID().toString());
        property.setDateListed(LocalDate.now().toString());
        property.setLandlord(landlord);

        //4. Save Property Entity
        Property savedProperty = propertyRepo.save(property);

        return this.modelMapper.map(savedProperty, PropertyDTO.class);

    }

    @Override
    public List<PropertyDTO> getAllProperties() {
        List<Property> allProperties = propertyRepo.findAll();
        return allProperties.stream().map(
                (property) -> this.modelMapper.map(property, PropertyDTO.class)
        ).toList();
    }

    @Override
    public List<PropertyDTO> getAllPropertiesOfLandlord(String landlordId) {
        Landlord landlord = landlordRepo.findById(landlordId).orElseThrow(
                () -> new ResourceNotFoundException
                        ("Landlord doesn't exist with id: " + landlordId)
        );

        List<Property> allProperties = propertyRepo.findByLandlordUserId(landlordId);
        return allProperties.stream().map((property) ->
                this.modelMapper.map(property, PropertyDTO.class)).toList();
    }

    @Override
    public PropertyDTO getPropertyById(String propertyId) {
        Property property = propertyRepo.findById(propertyId).orElseThrow(
                () -> new ResourceNotFoundException("Property doesn't exist with id: " + propertyId)
        );
        return this.modelMapper.map(property, PropertyDTO.class);
    }

    @Override
    public void deleteProperty(String propertyId) {
        Property property = propertyRepo.findById(propertyId).orElseThrow(
                () -> new ResourceNotFoundException("Property doesn't exist with id: " + propertyId)
        );

        propertyRepo.delete(property);
    }

    @Override
    public PropertyDTO updateProperty(String propertyId, PropertyUpdateDTO propertyUpdateDTO) {
        logger.info(propertyUpdateDTO.toString());
        Property property = propertyRepo.findById(propertyId).orElseThrow(
                () -> new ResourceNotFoundException("Property doesn't exist with id: " + propertyId)
        );
        logger.info(property.toString());
        // Update only the specified fields if they are not null or positive
        if (propertyUpdateDTO.getRent() != null) {
            property.setRent(Double.parseDouble(propertyUpdateDTO.getRent()));
        }
        if (propertyUpdateDTO.getDeposit() != null) {
            property.setDeposit(Double.parseDouble(propertyUpdateDTO.getDeposit()));
        }
        if (propertyUpdateDTO.getPhotos() != null) {
            property.setPhotos(propertyUpdateDTO.getPhotos());
        }
        if (propertyUpdateDTO.getAvailabilityStatus() != null) {
            property.setAvailabilityStatus(propertyUpdateDTO.getAvailabilityStatus());
        }

        // Save the updated property entity
        Property updatedProperty = propertyRepo.save(property);
        logger.info(updatedProperty.toString());
        return this.modelMapper.map(updatedProperty, PropertyDTO.class);
    }
}
