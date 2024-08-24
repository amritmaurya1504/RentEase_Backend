package com.rentease_server.server.services;
import com.rentease_server.server.dtos.requestdtos.PropertyReqDTO;
import com.rentease_server.server.dtos.commondtos.PropertyUpdateDTO;
import com.rentease_server.server.dtos.requestdtos.PropertyFilterDTO;
import com.rentease_server.server.dtos.responsedtos.PropertyResDTO;

import java.util.List;

public interface PropertyService {

    PropertyResDTO createProperty(PropertyReqDTO propertyDTO, String landlordId);
    List<PropertyResDTO> getAllProperties();
    List<PropertyResDTO> getAllPropertiesOfLandlord(String landlordId);
    PropertyResDTO getPropertyById(String propertyId);
    void deleteProperty(String propertyId);
    PropertyResDTO updateProperty(String propertyId, PropertyUpdateDTO propertyUpdateDTO);
    List<PropertyResDTO> getAllPropertiesWithFilters(PropertyFilterDTO propertyFilterDTO);

}
