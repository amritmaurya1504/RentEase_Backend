package com.rentease_server.server.controllers;

import com.rentease_server.server.dtos.requestdtos.UserUpdateReqDTO;
import com.rentease_server.server.dtos.responsedtos.UserResponseDTO;
import com.rentease_server.server.enums.Role;
import com.rentease_server.server.payloads.APIResponse;
import com.rentease_server.server.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "user-controller", description = "Operations related to user management")
public class UserController {

    @Autowired
    private UserService userService;



    @PutMapping("/{role}/{userId}")
    public APIResponse<UserResponseDTO> updateUser(@PathVariable Role role, @PathVariable String userId, @Valid @RequestBody
    UserUpdateReqDTO userUpdateReqDTO){
        return new APIResponse<>(role + " Updated Successfully !",
                true, HttpStatus.CREATED, this.userService
                .updateUser(userId, role, userUpdateReqDTO));
    }

    @GetMapping("/{role}/{userId}")
    public APIResponse<UserResponseDTO> getUser(@PathVariable Role role, @PathVariable String userId){
        return new APIResponse<>(role + " fetched successfully !", true
                , HttpStatus.OK, this.userService.getUserById(role, userId));
    }

    @DeleteMapping("/{role}/{userId}")
    public APIResponse<UserResponseDTO> deleteUser(@PathVariable Role role, @PathVariable String userId){
        return new APIResponse<>(role + " deleted successfully !", true, HttpStatus.OK, null);
    }

}
