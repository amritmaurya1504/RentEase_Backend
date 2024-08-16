package com.rentease_server.server.services;
import com.rentease_server.server.dtos.requestdtos.UserRegisterReqDTO;
import com.rentease_server.server.dtos.requestdtos.UserUpdateReqDTO;
import com.rentease_server.server.dtos.responsedtos.*;
import com.rentease_server.server.enums.Role;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRegisterReqDTO userRegisterReqDTO);
    UserResponseDTO updateUser(String userId, Role role, UserUpdateReqDTO userUpdateReqDTO);
    UserResponseDTO getUserById(Role role, String userId);
    List<UserResponseDTO> getAllUsers();
    void deleteUser(Role role, String userId);

}
