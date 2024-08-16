package com.rentease_server.server.services;
import com.rentease_server.server.dtos.commondtos.PropertyDTO;
import com.rentease_server.server.dtos.commondtos.PropertyUpdateDTO;

import java.util.List;

public interface PropertyService {

    PropertyDTO createProperty(PropertyDTO propertyDTO, String landlordId);
    List<PropertyDTO> getAllProperties();
    List<PropertyDTO> getAllPropertiesOfLandlord(String landlordId);
    PropertyDTO getPropertyById(String propertyId);
    void deleteProperty(String propertyId);
    PropertyDTO updateProperty(String propertyId, PropertyUpdateDTO propertyUpdateDTO);

}
