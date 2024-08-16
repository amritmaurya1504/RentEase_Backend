package com.rentease_server.server.controllers;

import com.rentease_server.server.dtos.commondtos.PropertyDTO;
import com.rentease_server.server.dtos.commondtos.PropertyUpdateDTO;
import com.rentease_server.server.payloads.APIResponse;
import com.rentease_server.server.services.PropertyService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/real-state/properties")
public class PropertyController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private PropertyService propertyService;

    @PostMapping("/{landlordId}")
    public APIResponse<PropertyDTO> addProperty(@PathVariable String landlordId, @Valid @RequestBody PropertyDTO propertyDTO){

        logger.info("Received request to create property with landlordId: {}", landlordId);
        PropertyDTO newProperty = propertyService.createProperty(propertyDTO, landlordId);
        if (newProperty == null) {
            return new APIResponse<>("Property creation failed!", false, HttpStatus.BAD_REQUEST, null);
        }
        return new APIResponse<>("Property Created Successfully!", true, HttpStatus.CREATED, newProperty);

    }

    @GetMapping
    public APIResponse<List<PropertyDTO>> getAllProperties(){
        List<PropertyDTO> allProperties = propertyService.getAllProperties();
        return new APIResponse<>(allProperties.isEmpty() ? "No properties listed yet!" : "All properties listed fetched successfully!",
                true, HttpStatus.OK, allProperties);
    }

    @GetMapping("/landlord/{landlordId}")
    public APIResponse<List<PropertyDTO>> getPropertiesOfLandlord(@PathVariable String landlordId){
        List<PropertyDTO> allProperties = propertyService.getAllPropertiesOfLandlord(landlordId);
        return new APIResponse<>(allProperties.isEmpty() ? "No properties listed yet!" : "Landlord properties fetched successfully!",
                true, HttpStatus.OK, allProperties);
    }

    @GetMapping("/{propertyId}")
    public APIResponse<PropertyDTO> getSingleProperty(@PathVariable String propertyId){
        PropertyDTO property = propertyService.getPropertyById(propertyId);
        return new APIResponse<>("Property Fetched Successfully!", true, HttpStatus.OK, property);
    }

    @DeleteMapping("/{propertyId}")
    public APIResponse<Void> deleteProperties(@PathVariable String propertyId){
        propertyService.deleteProperty(propertyId);
        return new APIResponse<>("Property Deleted Successfully!", true, HttpStatus.OK, null);
    }

    @PutMapping("/{propertyId}")
    public APIResponse<PropertyDTO> updateProperty(@PathVariable String propertyId,
                                                   @Valid @RequestBody PropertyUpdateDTO propertyUpdateDTO){
        PropertyDTO property = propertyService.updateProperty(propertyId, propertyUpdateDTO);
        return new APIResponse<>("Property Updated Successfully!", true, HttpStatus.OK, property);
    }

}
