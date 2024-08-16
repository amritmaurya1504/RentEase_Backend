package com.rentease_server.server.controllers;

import com.rentease_server.server.dtos.requestdtos.UserRegisterReqDTO;
import com.rentease_server.server.dtos.responsedtos.UserResponseDTO;
import com.rentease_server.server.payloads.APIResponse;
import com.rentease_server.server.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public APIResponse<UserResponseDTO> createUser(@Valid @RequestBody
                                                       UserRegisterReqDTO userRegisterReqDTO){
        UserResponseDTO createdUser = this.userService.createUser(userRegisterReqDTO);
        return new APIResponse<UserResponseDTO>("User Created Successfully!",
                true, HttpStatus.CREATED, createdUser);
    }
}
