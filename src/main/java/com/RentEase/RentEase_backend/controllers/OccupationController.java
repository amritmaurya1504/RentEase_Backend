package com.RentEase.RentEase_backend.controllers;

import com.RentEase.RentEase_backend.dtos.OccupationDTO;
import com.RentEase.RentEase_backend.payloads.APIResponse;
import com.RentEase.RentEase_backend.services.OccupationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/occupation")
@Tag(name = "Occupation Controller", description = "Operations related to occupations, accessible only by Tenant")
public class OccupationController {

    @Autowired
    private OccupationService occupationService;

    @PostMapping("/{tenantId}")
    public APIResponse<OccupationDTO> addOccupation(@PathVariable String tenantId, @Valid
                                                    @RequestBody OccupationDTO occupationDTO){
        OccupationDTO occupationDTO1 = occupationService.addOccupation(occupationDTO, tenantId);
        return new APIResponse<>("Occupation added Successfully!",
                true, HttpStatus.CREATED, occupationDTO1);
    }

    @GetMapping("/{occupationId}")
    public APIResponse<OccupationDTO> getOccupationByOccupationId(@PathVariable String occupationId){
        OccupationDTO occupation = occupationService.getOccupationByOccupationId(occupationId);
        return new APIResponse<>("Fetched Successfully!",
                true, HttpStatus.OK, occupation);
    }

    @PutMapping("/{occupationId}")
    public APIResponse<OccupationDTO> updateOccupation(@PathVariable String occupationId, @RequestBody
                                                       OccupationDTO occupationDTO){
        OccupationDTO occupation = occupationService.updateOccupation(occupationDTO, occupationId);
        return new APIResponse<>("Occupation Updated Successfully!",
                true, HttpStatus.CREATED, occupation);
    }

    @GetMapping("/tenant/{tenantId}")
    private APIResponse<OccupationDTO> getOccupationByTenantId(@PathVariable String tenantId){
        return new APIResponse<>("Fetched Successfully!",
                true, HttpStatus.OK, occupationService.getOccupationByTenantId(tenantId));
    }

}
