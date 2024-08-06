package com.RentEase.RentEase_backend.services;

import com.RentEase.RentEase_backend.dtos.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    UserDTO getUserById(String userId);
    List<UserDTO> getAllUsers();
    void deleteUser(String userId);

}
