package com.RentEase.RentEase_backend.controllers;

import com.RentEase.RentEase_backend.dtos.PropertyDTO;
import com.RentEase.RentEase_backend.dtos.PropertyUpdateDTO;
import com.RentEase.RentEase_backend.payloads.APIResponse;
import com.RentEase.RentEase_backend.services.PropertyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping("/{landlordId}")
    public APIResponse<PropertyDTO> createProperty(@PathVariable String landlordId,
                                                   @Valid @RequestBody PropertyDTO propertyDTO){
        PropertyDTO newProperty = propertyService.createProperty(propertyDTO, landlordId);
        return new APIResponse<>("Property Created Successfully!",
                true, HttpStatus.CREATED, newProperty);
    }

    @GetMapping
    public APIResponse<List<PropertyDTO>> getAllProperties(){
        List<PropertyDTO> allProperties = propertyService.getAllProperties();
        return new APIResponse<>("Fetched Successfully!", true, HttpStatus.OK,
                allProperties);
    }

    @GetMapping("/landlord/{landlordId}")
    public APIResponse<List<PropertyDTO>> getPropertiesOfLandlord(@PathVariable String landlordId){
        List<PropertyDTO> allProperties = propertyService.getAllPropertiesOfLandlord(landlordId);
        return new APIResponse<>("Fetched Successfully!", true, HttpStatus.OK,
                allProperties);
    }

    @GetMapping("/{propertyId}")
    public APIResponse<PropertyDTO> getSingleProperty(@PathVariable String propertyId){
        PropertyDTO property = propertyService.getPropertyById(propertyId);
        return new APIResponse<>("Property Deleted Successfully!", true, HttpStatus.OK,
                property);
    }

    @DeleteMapping("/{propertyId}")
    public APIResponse deleteProperties(@PathVariable String propertyId){
        propertyService.deleteProperty(propertyId);
        return new APIResponse<>("Property Deleted Successfully!", true, HttpStatus.OK,
                null);
    }

    @PutMapping("/{propertyId}")
    public APIResponse<PropertyDTO> updateProperty(@PathVariable String propertyId, @Valid @RequestBody PropertyUpdateDTO
                                                   propertyUpdateDTO){
        PropertyDTO property = propertyService.updateProperty(propertyId, propertyUpdateDTO);

        return new APIResponse<>("Property Updated Successfully!", true, HttpStatus.CREATED,
                property);
    }
}
