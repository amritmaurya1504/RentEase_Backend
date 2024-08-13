package com.RentEase.RentEase_backend.services;

import com.RentEase.RentEase_backend.dtos.requestdtos.OccupationReqDTO;
import com.RentEase.RentEase_backend.dtos.responsedtos.OccupationResDTO;

public interface OccupationService {

    OccupationResDTO addOccupation(OccupationReqDTO occupationReqDTO, String tenantId);
    OccupationResDTO getOccupationByOccupationId(String occupationId);
    OccupationResDTO updateOccupation(OccupationReqDTO occupationReqDTO, String occupationId);
    OccupationResDTO getOccupationByTenantId(String tenantId);

}
