package com.RentEase.RentEase_backend.services;

import com.RentEase.RentEase_backend.dtos.UserDTO;
import com.RentEase.RentEase_backend.dtos.UserUpdateDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(String userId, UserUpdateDTO userUpdateDTO);

    UserDTO getUserById(String userId);
    List<UserDTO> getAllUsers();
    void deleteUser(String userId);

    UserDTO getTenant(String tenantId);
    UserDTO getLandlord(String landlordId);

}
