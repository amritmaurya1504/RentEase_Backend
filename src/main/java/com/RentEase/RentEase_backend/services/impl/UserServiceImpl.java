package com.RentEase.RentEase_backend.services.impl;

import com.RentEase.RentEase_backend.dtos.UserDTO;
import com.RentEase.RentEase_backend.dtos.UserUpdateDTO;
import com.RentEase.RentEase_backend.entities.Landlord;
import com.RentEase.RentEase_backend.entities.Tenant;
import com.RentEase.RentEase_backend.entities.User;
import com.RentEase.RentEase_backend.enums.Role;
import com.RentEase.RentEase_backend.exceptions.ResourceNotFoundException;
import com.RentEase.RentEase_backend.exceptions.UserAlreadyExistsException;
import com.RentEase.RentEase_backend.repositories.LandlordRepo;
import com.RentEase.RentEase_backend.repositories.UserRepo;
import com.RentEase.RentEase_backend.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LandlordRepo landlordRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        //1. Check User with username or email already exist in db or not
        if(userRepo.existsByUserName(userDTO.getUsername())){
            throw new UserAlreadyExistsException("Username already exists with username: " +
                    userDTO.getUsername());
        }

        if (userRepo.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists with email: " +
                    userDTO.getEmail());
        }

        //2. Create User
        User user = User.builder()
                .userId(UUID.randomUUID().toString())
                .userName(userDTO.getUsername())
                .fullName(userDTO.getFullName())
                .email(userDTO.getEmail())
                .phone(Long.valueOf(userDTO.getPhone()))
                .photoUrl(userDTO.getPhotoUrl())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .address(userDTO.getAddress())
                .state(userDTO.getState())
                .city(userDTO.getCity())
                .pinCode(Integer.parseInt(userDTO.getPinCode())).build();

        //3. Check Role of User
        if (userDTO.getRole().equals(Role.Landlord)){
            user.setRole(Role.Landlord);
            Landlord landlord = Landlord.builder()
                    .landlordId(UUID.randomUUID().toString())
                    .user(user)
                    .joinedDate(LocalDate.now().toString()).build();

            user.setLandlord(landlord);

            //4. Save User and send response
            User newUser = userRepo.save(user);
            return this.modelMapper.map(newUser, UserDTO.class);
        }else if (userDTO.getRole().equals(Role.Tenant)){
            user.setRole(Role.Tenant);

            Tenant tenant = Tenant.builder()
                    .tenantId(UUID.randomUUID().toString())
                    .user(user)
                    .joinedDate(LocalDate.now().toString())
                    .build();

            user.setTenant(tenant);

            //4. Save User and send response
            User newUser = userRepo.save(user);
            return this.modelMapper.map(newUser, UserDTO.class);
        }

        return null;
    }

    @Override
    public UserDTO updateUser(String userId, UserUpdateDTO userUpdateDTO) {
        //1. First Check where user with give id exist or not;
        User existingUser = userRepo.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User does not exist with id: " + userId));

        // Update only the fields that are not null
        if (userUpdateDTO.getFullName() != null) {
            existingUser.setFullName(userUpdateDTO.getFullName());
        }
        if (userUpdateDTO.getPhotoUrl() != null) {
            existingUser.setPhotoUrl(userUpdateDTO.getPhotoUrl());
        }
        if (userUpdateDTO.getPhone() != null) {
            existingUser.setPhone(Long.valueOf(userUpdateDTO.getPhone()));
        }
        if (userUpdateDTO.getAddress() != null) {
            existingUser.setAddress(userUpdateDTO.getAddress());
        }
        if (userUpdateDTO.getCity() != null) {
            existingUser.setCity(userUpdateDTO.getCity());
        }
        if (userUpdateDTO.getPinCode() != null) {
            existingUser.setPinCode(Integer.parseInt(userUpdateDTO.getPinCode()));
        }
        if (userUpdateDTO.getState() != null) {
            existingUser.setState(userUpdateDTO.getState());
        }

        // Save the updated user to the database
        User updatedUser = userRepo.save(existingUser);

        // Return the updated user as a DTO
        return this.modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(String userId) {

        //1. First Check where user with give id exist or not;
        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User does not exist with id: " + userId));

        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream().map(
                user -> this.modelMapper.map(user, UserDTO.class))
                .toList();
    }

    @Override
    public void deleteUser(String userId) {

        //1. First Check where user with give id exist or not;
        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User does not exist with id: " + userId));

        userRepo.delete(user);
    }
}
