package com.RentEase.RentEase_backend.services;

import com.RentEase.RentEase_backend.dtos.OccupationDTO;
import com.RentEase.RentEase_backend.entities.Occupation;

import java.util.List;

public interface OccupationService {

    OccupationDTO addOccupation(OccupationDTO occupationDTO, String tenantId);
    OccupationDTO getOccupationByOccupationId(String occupationId);
    OccupationDTO updateOccupation(OccupationDTO occupationDTO, String occupationId);
    OccupationDTO getOccupationByTenantId(String tenantId);

}
