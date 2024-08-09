package com.RentEase.RentEase_backend.controllers;

import com.RentEase.RentEase_backend.dtos.OccupationDTO;
import com.RentEase.RentEase_backend.dtos.UserDTO;
import com.RentEase.RentEase_backend.payloads.APIResponse;
import com.RentEase.RentEase_backend.services.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantController {
    @Autowired
    private TenantService tenantService;

    @GetMapping("/{tenantId}")
    public APIResponse<UserDTO> getTenant(@PathVariable String tenantId){
        return new APIResponse<>("Fetched Successfully!",
                true, HttpStatus.OK, tenantService.getTenant(tenantId));
    }

    @GetMapping("/occupation/{tenantId}")
    private APIResponse<OccupationDTO> getOccupationByTenantId(@PathVariable String tenantId){
        return new APIResponse<>("Fetched Successfully!",
                true, HttpStatus.OK, tenantService.getOccupationById(tenantId));
    }
}
