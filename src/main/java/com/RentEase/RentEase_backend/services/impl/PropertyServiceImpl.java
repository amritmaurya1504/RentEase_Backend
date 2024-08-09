package com.RentEase.RentEase_backend.services.impl;

import com.RentEase.RentEase_backend.dtos.PropertyDTO;
import com.RentEase.RentEase_backend.dtos.PropertyUpdateDTO;
import com.RentEase.RentEase_backend.entities.Landlord;
import com.RentEase.RentEase_backend.entities.Property;
import com.RentEase.RentEase_backend.entities.User;
import com.RentEase.RentEase_backend.enums.Role;
import com.RentEase.RentEase_backend.exceptions.ResourceNotFoundException;
import com.RentEase.RentEase_backend.exceptions.UserNotAuthorizedException;
import com.RentEase.RentEase_backend.repositories.LandlordRepo;
import com.RentEase.RentEase_backend.repositories.PropertyRepo;
import com.RentEase.RentEase_backend.services.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        if(landlord.getUser().getRole().equals(Role.Landlord)){
            //2. Convert DTO to Entity
            Property property = this.modelMapper.map(propertyDTO, Property.class);
            //3. Add Landlord to property
            property.setPropertyId(UUID.randomUUID().toString());
            property.setLandlord(landlord);
            property.setDateListed(LocalDate.now().toString());

            //4. Save Property Entity
            Property savedProperty = propertyRepo.save(property);

            return this.modelMapper.map(savedProperty, PropertyDTO.class);
        }

        throw new UserNotAuthorizedException("Unauthorized User!");

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

        List<Property> allProperties = propertyRepo.findByLandlordLandlordId(landlordId);
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
        if (propertyUpdateDTO.getRentPerSquareFt() > 0) {
            property.setRentPerSquareFt(propertyUpdateDTO.getRentPerSquareFt());
        }
        if (propertyUpdateDTO.getDeposit() > 0) {
            property.setDeposit(propertyUpdateDTO.getDeposit());
        }
        if (propertyUpdateDTO.getConfiguration() != null) {
            property.setConfiguration(propertyUpdateDTO.getConfiguration());
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
