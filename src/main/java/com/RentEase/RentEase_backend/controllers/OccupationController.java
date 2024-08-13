package com.RentEase.RentEase_backend.controllers;

import com.RentEase.RentEase_backend.dtos.requestdtos.OccupationReqDTO;
import com.RentEase.RentEase_backend.dtos.responsedtos.OccupationResDTO;
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
    public APIResponse<OccupationResDTO> addOccupation(@PathVariable String tenantId, @Valid
                                                    @RequestBody OccupationReqDTO occupationReqDTO){
        OccupationResDTO occupationReqDTO1 = occupationService.addOccupation(occupationReqDTO, tenantId);
        return new APIResponse<>("Occupation added Successfully!",
                true, HttpStatus.CREATED, occupationReqDTO1);
    }

    @GetMapping("/{occupationId}")
    public APIResponse<OccupationResDTO> getOccupationByOccupationId(@PathVariable String occupationId){
        OccupationResDTO occupation = occupationService.getOccupationByOccupationId(occupationId);
        return new APIResponse<>("Fetched Successfully!",
                true, HttpStatus.OK, occupation);
    }

    @PutMapping("/{occupationId}")
    public APIResponse<OccupationResDTO> updateOccupation(@PathVariable String occupationId, @RequestBody
    OccupationReqDTO occupationReqDTO){
        OccupationResDTO occupation = occupationService.updateOccupation(occupationReqDTO, occupationId);
        return new APIResponse<>("Occupation Updated Successfully!",
                true, HttpStatus.CREATED, occupation);
    }

    @GetMapping("/tenant/{tenantId}")
    private APIResponse<OccupationResDTO> getOccupationByTenantId(@PathVariable String tenantId){
        OccupationResDTO occupationResDTO = occupationService.getOccupationByTenantId(tenantId);
        return new APIResponse<>(occupationResDTO == null ? "Occupation doesn't exist !" : "Fetched Successfully !",
                true, HttpStatus.OK, occupationResDTO);
    }

}
