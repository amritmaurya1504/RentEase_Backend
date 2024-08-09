package com.RentEase.RentEase_backend.services.impl;

import com.RentEase.RentEase_backend.dtos.OccupationDTO;
import com.RentEase.RentEase_backend.entities.Occupation;
import com.RentEase.RentEase_backend.entities.Tenant;
import com.RentEase.RentEase_backend.exceptions.ResourceNotFoundException;
import com.RentEase.RentEase_backend.repositories.OccupationRepo;
import com.RentEase.RentEase_backend.repositories.TenantRepo;
import com.RentEase.RentEase_backend.services.OccupationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OccupationServiceImpl implements OccupationService {

    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private OccupationRepo occupationRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public OccupationDTO addOccupation(OccupationDTO occupationDTO, String tenantId) {

        Tenant tenant = tenantRepo.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant doesn't exists with id: " + tenantId));

        Occupation newOccupation = Occupation.builder()
                .occupationId(UUID.randomUUID().toString())
                .institutionName(occupationDTO.getInstitutionName())
                .institutionAddress(occupationDTO.getInstitutionAddress())
                .occupation(occupationDTO.getOccupation())
                .tenant(tenant).build();

        Occupation savedOccupation = occupationRepo.save(newOccupation);

        return this.modelMapper.map(savedOccupation, OccupationDTO.class);
    }

    @Override
    public OccupationDTO getOccupationByOccupationId(String occupationId) {
        Occupation occupation = occupationRepo.findById(occupationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Occupation doesn't exists with id: " + occupationId));

        return this.modelMapper.map(occupation, OccupationDTO.class);
    }

    @Override
    public OccupationDTO updateOccupation(OccupationDTO occupationDTO, String occupationId) {
        Occupation occupation = occupationRepo.findById(occupationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Occupation doesn't exists with id: " + occupationId));

        if (occupationDTO.getInstitutionName() != null){
            occupation.setInstitutionName(occupationDTO.getInstitutionName());
        }

        if (occupationDTO.getInstitutionAddress() != null){
            occupation.setInstitutionAddress(occupationDTO.getInstitutionAddress());
        }

        if (occupationDTO.getOccupation() != null){
            occupation.setOccupation(occupationDTO.getOccupation());
        }

        Occupation updatedOccupation = occupationRepo.save(occupation);

        return this.modelMapper.map(updatedOccupation, OccupationDTO.class);
    }

    @Override
    public OccupationDTO getOccupationByTenantId(String tenantId) {
        Tenant tenant = tenantRepo.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant doesn't exists with id: " + tenantId));

        Occupation occupation = occupationRepo.findByTenantTenantId(tenantId);

        return this.modelMapper.map(occupation, OccupationDTO.class);
    }


}
