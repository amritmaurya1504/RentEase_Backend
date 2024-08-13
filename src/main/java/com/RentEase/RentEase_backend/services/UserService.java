package com.RentEase.RentEase_backend.services;

import com.RentEase.RentEase_backend.dtos.requestdtos.UserRegisterReqDTO;
import com.RentEase.RentEase_backend.dtos.requestdtos.UserUpdateReqDTO;
import com.RentEase.RentEase_backend.dtos.responsedtos.UserResponseDTO;
import com.RentEase.RentEase_backend.enums.Role;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRegisterReqDTO userRegisterReqDTO);
    UserResponseDTO updateUser(String userId, Role role, UserUpdateReqDTO userUpdateReqDTO);
    UserResponseDTO getUserById(Role role, String userId);
    List<UserResponseDTO> getAllUsers();
    void deleteUser(Role role, String userId);

}
