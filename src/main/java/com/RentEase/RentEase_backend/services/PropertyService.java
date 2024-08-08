package com.RentEase.RentEase_backend.services;
import com.RentEase.RentEase_backend.dtos.PropertyDTO;
import com.RentEase.RentEase_backend.dtos.PropertyUpdateDTO;

import java.util.List;

public interface PropertyService {

    PropertyDTO createProperty(PropertyDTO propertyDTO, String landlordId);
    List<PropertyDTO> getAllProperties();
    List<PropertyDTO> getAllPropertiesOfLandlord(String landlordId);
    PropertyDTO getPropertyById(String propertyId);
    void deleteProperty(String propertyId);
    PropertyDTO updateProperty(String propertyId, PropertyUpdateDTO propertyUpdateDTO);

}
