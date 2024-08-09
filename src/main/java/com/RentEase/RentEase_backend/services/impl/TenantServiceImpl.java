package com.RentEase.RentEase_backend.services.impl;

import com.RentEase.RentEase_backend.dtos.OccupationDTO;
import com.RentEase.RentEase_backend.dtos.UserDTO;
import com.RentEase.RentEase_backend.entities.Occupation;
import com.RentEase.RentEase_backend.entities.Tenant;
import com.RentEase.RentEase_backend.entities.User;
import com.RentEase.RentEase_backend.exceptions.ResourceNotFoundException;
import com.RentEase.RentEase_backend.repositories.OccupationRepo;
import com.RentEase.RentEase_backend.repositories.TenantRepo;
import com.RentEase.RentEase_backend.repositories.UserRepo;
import com.RentEase.RentEase_backend.services.TenantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantRepo tenantRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OccupationRepo occupationRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO getTenant(String tenantId) {
        Tenant tenant = tenantRepo.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant doesn't exists with id: " + tenantId));

        User user = userRepo.findByTenantTenantId(tenantId);
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public OccupationDTO getOccupationById(String tenantId) {
        Tenant tenant = tenantRepo.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant doesn't exists with id: " + tenantId));

        Occupation occupation = occupationRepo.findByTenantTenantId(tenantId);

        return this.modelMapper.map(occupation, OccupationDTO.class);
    }
}
