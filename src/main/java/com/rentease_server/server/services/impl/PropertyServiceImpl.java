package com.rentease_server.server.services.impl;

import com.rentease_server.server.dtos.requestdtos.PropertyReqDTO;
import com.rentease_server.server.dtos.commondtos.PropertyUpdateDTO;
import com.rentease_server.server.dtos.requestdtos.PropertyFilterDTO;
import com.rentease_server.server.dtos.responsedtos.PropertyResDTO;
import com.rentease_server.server.entities.Landlord;
import com.rentease_server.server.entities.Property;
import com.rentease_server.server.exceptions.ResourceNotFoundException;
import com.rentease_server.server.repositories.LandlordRepo;
import com.rentease_server.server.repositories.PropertyRepo;
import com.rentease_server.server.services.PropertyService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

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
    public PropertyResDTO createProperty(PropertyReqDTO propertyDTO, String landlordId) {
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

        return this.modelMapper.map(savedProperty, PropertyResDTO.class);

    }

    @Override
    public List<PropertyResDTO> getAllProperties() {
        List<Property> allProperties = propertyRepo.findAll();
        return allProperties.stream().map(
                (property) -> this.modelMapper.map(property, PropertyResDTO.class)
        ).toList();
    }

    @Override
    public List<PropertyResDTO> getAllPropertiesOfLandlord(String landlordId) {
        Landlord landlord = landlordRepo.findById(landlordId).orElseThrow(
                () -> new ResourceNotFoundException
                        ("Landlord doesn't exist with id: " + landlordId)
        );

        List<Property> allProperties = propertyRepo.findByLandlordUserId(landlordId);
        return allProperties.stream().map((property) ->
                this.modelMapper.map(property, PropertyResDTO.class)).toList();
    }

    @Override
    public PropertyResDTO getPropertyById(String propertyId) {
        Property property = propertyRepo.findById(propertyId).orElseThrow(
                () -> new ResourceNotFoundException("Property doesn't exist with id: " + propertyId)
        );
        return this.modelMapper.map(property, PropertyResDTO.class);
    }

    @Override
    public void deleteProperty(String propertyId) {
        Property property = propertyRepo.findById(propertyId).orElseThrow(
                () -> new ResourceNotFoundException("Property doesn't exist with id: " + propertyId)
        );

        propertyRepo.delete(property);
    }

    @Override
    public PropertyResDTO updateProperty(String propertyId, PropertyUpdateDTO propertyUpdateDTO) {

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
        return this.modelMapper.map(updatedProperty, PropertyResDTO.class);
    }

    @Override
    public List<PropertyResDTO> getAllPropertiesWithFilters(PropertyFilterDTO filterDTO) {
        // Logic to apply filters
        List<Property> filteredProperties = propertyRepo.findAll().stream()
                .filter(property -> filterDTO.getMinBudget() == null || property.getRent() >= filterDTO.getMinBudget())
                .filter(property -> filterDTO.getMaxBudget() == null || property.getRent() <= filterDTO.getMaxBudget())
                .filter(property -> filterDTO.getPropertyTypes() == null || filterDTO.getPropertyTypes().contains(property.getPropertyType()))
                .filter(property -> filterDTO.getSize() == null || filterDTO.getSize().contains(property.getSize()))
                .filter(property -> filterDTO.getFurnishedStatuses() == null || filterDTO.getFurnishedStatuses().contains(property.getFurnishedStatus()))
                .filter(property -> filterDTO.getAvailabilityStatus() == null || property.getAvailabilityStatus().equals(filterDTO.getAvailabilityStatus()))
                .filter(property -> filterDTO.getTenantTypes() == null || filterDTO.getTenantTypes().contains(property.getTenantType()))
                .filter(property -> {
                    if (filterDTO.getAmenities() == null) {
                        return true;
                    }
                    List<String> propertyAmenities = Arrays.asList(property.getOtherAmenities());
                    logger.info(propertyAmenities.toString());
                    return new HashSet<>(propertyAmenities).containsAll(filterDTO.getAmenities());
                })
                .filter(property -> filterDTO.getCity() == null || property.getCity().equalsIgnoreCase(filterDTO.getCity()))
                .filter(property -> filterDTO.getState() == null || property.getState().equalsIgnoreCase(filterDTO.getState()))
                .toList();


        // Convert to mutable list if needed
        List<Property> mutableFilteredProperties = new ArrayList<>(filteredProperties);

        // Apply sorting based on the 'sortBy' criteria
        if (filterDTO.getSortBy() != null) {
            switch (filterDTO.getSortBy().toLowerCase()) {
                case "newest-first":
                    mutableFilteredProperties.sort(Comparator.comparing(Property::getDateListed).reversed());
                    break;
                case "price-high-to-low":
                    mutableFilteredProperties.sort(Comparator.comparing(Property::getRent).reversed());
                    break;
                case "price-low-to-high":
                    mutableFilteredProperties.sort(Comparator.comparing(Property::getRent));
                    break;
                default:
                    // No sorting if criteria is not matched
                    break;
            }
        }

        return mutableFilteredProperties.stream().map(item ->
                this.modelMapper.map(item, PropertyResDTO.class)).toList();

    }
}
