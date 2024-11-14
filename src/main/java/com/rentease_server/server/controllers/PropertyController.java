package com.rentease_server.server.controllers;

import com.rentease_server.server.dtos.requestdtos.EmailEnquiryDTO;
import com.rentease_server.server.dtos.requestdtos.PropertyReqDTO;
import com.rentease_server.server.dtos.commondtos.PropertyUpdateDTO;
import com.rentease_server.server.dtos.requestdtos.PropertyFilterDTO;
import com.rentease_server.server.dtos.responsedtos.PropertyResDTO;
import com.rentease_server.server.payloads.APIResponse;
import com.rentease_server.server.services.EmailService;
import com.rentease_server.server.services.PropertyService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/real-estate/properties")
public class PropertyController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private PropertyService propertyService;



    @PostMapping("/{landlordId}")
    public APIResponse<PropertyResDTO> addProperty(@PathVariable String landlordId, @Valid @RequestBody PropertyReqDTO propertyDTO){

        logger.info("Received request to create property with landlordId: {}", landlordId);
        PropertyResDTO newProperty = propertyService.createProperty(propertyDTO, landlordId);
        if (newProperty == null) {
            return new APIResponse<>("Property creation failed!", false, HttpStatus.BAD_REQUEST, null);
        }
        return new APIResponse<>("Property Created Successfully!", true, HttpStatus.CREATED, newProperty);

    }

    @GetMapping
    public APIResponse<List<PropertyResDTO>> getAllProperties(){
        List<PropertyResDTO> allProperties = propertyService.getAllProperties();
        return new APIResponse<>(allProperties.isEmpty() ? "No properties listed yet!" : allProperties.size() + " properties listed fetched successfully!",
                true, HttpStatus.OK, allProperties);
    }

    @GetMapping("/landlord/{landlordId}")
    public APIResponse<List<PropertyResDTO>> getPropertiesOfLandlord(@PathVariable String landlordId){
        List<PropertyResDTO> allProperties = propertyService.getAllPropertiesOfLandlord(landlordId);
        return new APIResponse<>(allProperties.isEmpty() ? "No properties listed yet!" : + allProperties.size() + " Landlord properties fetched successfully!",
                true, HttpStatus.OK, allProperties);
    }

    @GetMapping("/{propertyId}")
    public APIResponse<PropertyResDTO> getSingleProperty(@PathVariable String propertyId){
        PropertyResDTO property = propertyService.getPropertyById(propertyId);
        return new APIResponse<>("Property Fetched Successfully!", true, HttpStatus.OK, property);
    }

    @DeleteMapping("/{propertyId}")
    public APIResponse<Void> deleteProperties(@PathVariable String propertyId){
        propertyService.deleteProperty(propertyId);
        return new APIResponse<>("Property Deleted Successfully!", true, HttpStatus.OK, null);
    }

    @PutMapping("/{propertyId}")
    public APIResponse<PropertyResDTO> updateProperty(@PathVariable String propertyId,
                                                   @Valid @RequestBody PropertyUpdateDTO propertyUpdateDTO){
        PropertyResDTO property = propertyService.updateProperty(propertyId, propertyUpdateDTO);
        return new APIResponse<>("Property Updated Successfully!", true, HttpStatus.OK, property);
    }

    @GetMapping("/filter")
    public APIResponse<List<PropertyResDTO>> getPropertiesBasedOnFilters(
            @RequestParam(required = false) Double minBudget,
            @RequestParam(required = false) Double maxBudget,
            @RequestParam(required = false) List<String> propertyTypes,
            @RequestParam(required = false) List<String> size,
            @RequestParam(required = false) List<String> furnishedStatuses,
            @RequestParam(required = false) String availabilityStatus,
            @RequestParam(required = false) List<String> amenities,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) List<String> tenantTypes
    ){
        PropertyFilterDTO filterDTO = PropertyFilterDTO.builder()
                .minBudget(minBudget)
                .maxBudget(maxBudget)
                .propertyTypes(propertyTypes)
                .size(size)
                .furnishedStatuses(furnishedStatuses)
                .availabilityStatus(availabilityStatus)
                .amenities(amenities)
                .city(city)
                .state(state)
                .sortBy(sortBy)
                .tenantTypes(tenantTypes)
                .build();

        List<PropertyResDTO> data = this.propertyService.getAllPropertiesWithFilters(filterDTO);
        return new APIResponse<>(data.isEmpty() ? "No Property listed with given filters !" : + data.size() + " Data Fetched successfully!" , true,
                HttpStatus.OK, data);
    }

}
