package com.RentEase.RentEase_backend.services.impl;

import com.RentEase.RentEase_backend.dtos.requestdtos.UserRegisterReqDTO;
import com.RentEase.RentEase_backend.dtos.requestdtos.UserUpdateReqDTO;
import com.RentEase.RentEase_backend.dtos.responsedtos.UserResponseDTO;
import com.RentEase.RentEase_backend.entities.Landlord;
import com.RentEase.RentEase_backend.entities.Tenant;
import com.RentEase.RentEase_backend.enums.Role;
import com.RentEase.RentEase_backend.exceptions.ResourceNotFoundException;
import com.RentEase.RentEase_backend.exceptions.RoleNotExistException;
import com.RentEase.RentEase_backend.exceptions.UserAlreadyExistsException;
import com.RentEase.RentEase_backend.repositories.LandlordRepo;
import com.RentEase.RentEase_backend.repositories.TenantRepo;
import com.RentEase.RentEase_backend.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private LandlordRepo landlordRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserResponseDTO createUser(UserRegisterReqDTO userRegisterReqDTO) {

        //1. first check role
        if(userRegisterReqDTO.getRole().equals(Role.Tenant)){
            //2. Check tenant exists with userName or email or not
            if(tenantRepo.existsByUserName(userRegisterReqDTO.getUsername())){
                throw new UserAlreadyExistsException("User already exists with username: "+ userRegisterReqDTO.getUsername());
            }

            if(tenantRepo.existsByEmail(userRegisterReqDTO.getEmail())){
                throw new UserAlreadyExistsException("User already exists with email: "+ userRegisterReqDTO.getEmail());
            }

            //3. Create Tenant;
            Tenant tenant = new Tenant();
            tenant.setUserId(UUID.randomUUID().toString());
            tenant.setFullName(userRegisterReqDTO.getFullName());
            tenant.setUserName(userRegisterReqDTO.getUsername());
            tenant.setPassword(passwordEncoder.encode(userRegisterReqDTO.getPassword()));
            tenant.setRole(Role.Tenant);
            tenant.setEmail(userRegisterReqDTO.getEmail());
            tenant.setJoinedDate(LocalDate.now().toString());
            tenant.setUserActivated(false);

            //4. Save Tenant
            tenantRepo.save(tenant);

            //5. Send Response
            return this.modelMapper.map(tenant, UserResponseDTO.class);
        }

        if(userRegisterReqDTO.getRole().equals(Role.Landlord)){
            //2. Check tenant exists with userName or email or not
            if(landlordRepo.existsByUserName(userRegisterReqDTO.getUsername())){
                throw new UserAlreadyExistsException("User already exists with username: "+ userRegisterReqDTO.getUsername());
            }

            if(landlordRepo.existsByEmail(userRegisterReqDTO.getEmail())){
                throw new UserAlreadyExistsException("User already exists with email: "+ userRegisterReqDTO.getEmail());
            }

            //3. Create Tenant;
            Landlord landlord = new Landlord();
            landlord.setUserId(UUID.randomUUID().toString());
            landlord.setFullName(userRegisterReqDTO.getFullName());
            landlord.setUserName(userRegisterReqDTO.getUsername());
            landlord.setPassword(passwordEncoder.encode(userRegisterReqDTO.getPassword()));
            landlord.setRole(Role.Landlord);
            landlord.setEmail(userRegisterReqDTO.getEmail());
            landlord.setJoinedDate(LocalDate.now().toString());
            landlord.setUserActivated(false);

            //4. Save Tenant
            landlordRepo.save(landlord);

            //5. Send Response
            return this.modelMapper.map(landlord, UserResponseDTO.class);
        }

        return null;
    }

    @Override
    public UserResponseDTO updateUser(String userId, Role role, UserUpdateReqDTO userUpdateReqDTO) {

        if(!role.equals(Role.Tenant) && !role.equals(Role.Landlord)){
            throw new RoleNotExistException("Role : [" + role + "] not exist !");
        }

        //1. First Check where user with give id exist or not;
        if(role.equals(Role.Tenant)){
            Tenant tenant = tenantRepo.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Tenant doesn't exist with id: " + userId
                    ));

            // Update only the fields that are not null
            if (userUpdateReqDTO.getFullName() != null) {
                tenant.setFullName(userUpdateReqDTO.getFullName());
            }
            if (userUpdateReqDTO.getPhotoUrl() != null) {
                tenant.setPhotoUrl(userUpdateReqDTO.getPhotoUrl());
            }
            if (userUpdateReqDTO.getPhone() != null) {
                tenant.setPhone(userUpdateReqDTO.getPhone());
            }
            if (userUpdateReqDTO.getAddress() != null) {
                tenant.setAddress(userUpdateReqDTO.getAddress());
            }
            if (userUpdateReqDTO.getCity() != null) {
                tenant.setCity(userUpdateReqDTO.getCity());
            }
            if (userUpdateReqDTO.getPinCode() != null) {
                tenant.setPinCode(userUpdateReqDTO.getPinCode());
            }
            if (userUpdateReqDTO.getState() != null) {
                tenant.setState(userUpdateReqDTO.getState());
            }

            // Check if all required fields are non-null and then activate the user
            if (tenant.getPhone() != null && tenant.getAddress() != null &&
                    tenant.getCity() != null &&
                    tenant.getPinCode() != null && tenant.getState() != null) {
                tenant.setUserActivated(true);
            }

            // Save the updated user to the database
            Tenant updatedTenant = tenantRepo.save(tenant);

            // Return the updated user as a DTO
            return this.modelMapper.map(updatedTenant, UserResponseDTO.class);

        }

        if(role.equals(Role.Landlord)){
            Landlord landlord = landlordRepo.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Landlord doesn't exist with id: " + userId
                    ));

            // Update only the fields that are not null
            if (userUpdateReqDTO.getFullName() != null) {
                landlord.setFullName(userUpdateReqDTO.getFullName());
            }
            if (userUpdateReqDTO.getPhotoUrl() != null) {
                landlord.setPhotoUrl(userUpdateReqDTO.getPhotoUrl());
            }
            if (userUpdateReqDTO.getPhone() != null) {
                landlord.setPhone(userUpdateReqDTO.getPhone());
            }
            if (userUpdateReqDTO.getAddress() != null) {
                landlord.setAddress(userUpdateReqDTO.getAddress());
            }
            if (userUpdateReqDTO.getCity() != null) {
                landlord.setCity(userUpdateReqDTO.getCity());
            }
            if (userUpdateReqDTO.getPinCode() != null) {
                landlord.setPinCode(userUpdateReqDTO.getPinCode());
            }
            if (userUpdateReqDTO.getState() != null) {
                landlord.setState(userUpdateReqDTO.getState());
            }

            // Check if all required fields are non-null and then activate the user
            if (landlord.getPhone() != null && landlord.getAddress() != null &&
                    landlord.getCity() != null &&
                    landlord.getPinCode() != null && landlord.getState() != null) {
                landlord.setUserActivated(true);
            }

            // Save the updated user to the database
            Landlord updatedLandlord = landlordRepo.save(landlord);

            // Return the updated user as a DTO
            return this.modelMapper.map(updatedLandlord, UserResponseDTO.class);
        }

        return null;
    }

    @Override
    public UserResponseDTO getUserById(Role role, String userId) {

        if(!role.equals(Role.Tenant) && !role.equals(Role.Landlord)){
            throw new RoleNotExistException("Role : [" + role + "] not exist !");
        }

        if(role.equals(Role.Tenant)){
            Tenant tenant = tenantRepo.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("Tenant doesn't exist with id: " + userId)
            );

            return this.modelMapper.map(tenant, UserResponseDTO.class);
        }

        Landlord landlord = landlordRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Landlord doesn't exist with id: " + userId)
        );

        return this.modelMapper.map(landlord, UserResponseDTO.class);


    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return null;
    }

    @Override
    public void deleteUser(Role role, String userId) {
        if(!role.equals(Role.Tenant) && !role.equals(Role.Landlord)){
            throw new RoleNotExistException("Role : [" + role + "] not exist !");
        }

        if(role.equals(Role.Tenant)){
            Tenant tenant = tenantRepo.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("Tenant doesn't exist with id: " + userId)
            );

            tenantRepo.delete(tenant);
        }

        if(role.equals(Role.Landlord)){
            Landlord landlord = landlordRepo.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("Landlord doesn't exist with id: " + userId)
            );

            landlordRepo.delete(landlord);
        }
    }

}
