package com.RentEase.RentEase_backend.services;

import com.RentEase.RentEase_backend.dtos.OccupationDTO;
import com.RentEase.RentEase_backend.dtos.UserDTO;
import com.RentEase.RentEase_backend.entities.User;

public interface TenantService {

    UserDTO getTenant(String tenantId);
    OccupationDTO getOccupationById(String tenantId);

}
