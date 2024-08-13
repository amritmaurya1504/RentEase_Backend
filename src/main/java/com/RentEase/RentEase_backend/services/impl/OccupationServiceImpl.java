package com.RentEase.RentEase_backend.services.impl;

import com.RentEase.RentEase_backend.dtos.requestdtos.OccupationReqDTO;
import com.RentEase.RentEase_backend.dtos.responsedtos.OccupationResDTO;
import com.RentEase.RentEase_backend.entities.Occupation;
import com.RentEase.RentEase_backend.entities.Tenant;
import com.RentEase.RentEase_backend.exceptions.ResourceNotFoundException;
import com.RentEase.RentEase_backend.repositories.OccupationRepo;
import com.RentEase.RentEase_backend.repositories.TenantRepo;
import com.RentEase.RentEase_backend.services.OccupationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OccupationServiceImpl implements OccupationService {

    private static final Logger logger = LoggerFactory.getLogger(OccupationServiceImpl.class);
    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private OccupationRepo occupationRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public OccupationResDTO addOccupation(OccupationReqDTO occupationReqDTO, String tenantId) {

        Tenant tenant = tenantRepo.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant doesn't exists with id: " + tenantId));

        Occupation newOccupation = Occupation.builder()
                .occupationId(UUID.randomUUID().toString())
                .institutionName(occupationReqDTO.getInstitutionName())
                .institutionAddress(occupationReqDTO.getInstitutionAddress())
                .occupation(occupationReqDTO.getOccupation())
                .tenant(tenant).build();

        Occupation savedOccupation = occupationRepo.save(newOccupation);

        return this.modelMapper.map(savedOccupation, OccupationResDTO.class);
    }

    @Override
    public OccupationResDTO getOccupationByOccupationId(String occupationId) {
        Occupation occupation = occupationRepo.findById(occupationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Occupation doesn't exists with id: " + occupationId));

        return this.modelMapper.map(occupation, OccupationResDTO.class);
    }

    @Override
    public OccupationResDTO updateOccupation(OccupationReqDTO occupationReqDTO, String occupationId) {
        Occupation occupation = occupationRepo.findById(occupationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Occupation doesn't exists with id: " + occupationId));

        if (occupationReqDTO.getInstitutionName() != null){
            occupation.setInstitutionName(occupationReqDTO.getInstitutionName());
        }

        if (occupationReqDTO.getInstitutionAddress() != null){
            occupation.setInstitutionAddress(occupationReqDTO.getInstitutionAddress());
        }

        if (occupationReqDTO.getOccupation() != null){
            occupation.setOccupation(occupationReqDTO.getOccupation());
        }

        Occupation updatedOccupation = occupationRepo.save(occupation);

        return this.modelMapper.map(updatedOccupation, OccupationResDTO.class);
    }

    @Override
    public OccupationResDTO getOccupationByTenantId(String tenantId) {
        Tenant tenant = tenantRepo.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant doesn't exists with id: " + tenantId));
        Occupation occupation = occupationRepo.findByTenantUserId(tenantId);
        if(occupation == null){
            return null;
        }

        return this.modelMapper.map(occupation, OccupationResDTO.class);
    }


}
