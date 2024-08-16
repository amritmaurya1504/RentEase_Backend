package com.rentease_server.server.services.impl;

import com.rentease_server.server.dtos.commondtos.PropertyDTO;
import com.rentease_server.server.dtos.commondtos.PropertyUpdateDTO;
import com.rentease_server.server.entities.Landlord;
import com.rentease_server.server.entities.Property;
import com.rentease_server.server.exceptions.ResourceNotFoundException;
import com.rentease_server.server.repositories.LandlordRepo;
import com.rentease_server.server.repositories.PropertyRepo;
import com.rentease_server.server.services.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PropertyServiceImpl implements PropertyService {

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
        property.setDateListed(String.valueOf(LocalDateTime.now()));
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

        Property property = propertyRepo.findById(propertyId).orElseThrow(
                () -> new ResourceNotFoundException("Property doesn't exist with id: " + propertyId)
        );

        // Update only the specified fields if they are not null or positive
        if (propertyUpdateDTO.getRent() > 0) {
            property.setRent(propertyUpdateDTO.getRent());
        }
        if (propertyUpdateDTO.getDeposit() > 0) {
            property.setDeposit(propertyUpdateDTO.getDeposit());
        }
        if (propertyUpdateDTO.getPhotos() != null) {
            property.setPhotos(propertyUpdateDTO.getPhotos());
        }
        if (propertyUpdateDTO.getAvailabilityStatus() != null) {
            property.setAvailabilityStatus(propertyUpdateDTO.getAvailabilityStatus());
        }

        // Save the updated property entity
        Property updatedProperty = propertyRepo.save(property);
        return this.modelMapper.map(updatedProperty, PropertyDTO.class);
    }
}