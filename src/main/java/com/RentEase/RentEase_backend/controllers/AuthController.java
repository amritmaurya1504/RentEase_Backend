package com.RentEase.RentEase_backend.controllers;

import com.RentEase.RentEase_backend.dtos.UserAuthResponseDTO;
import com.RentEase.RentEase_backend.dtos.UserDTO;
import com.RentEase.RentEase_backend.dtos.UserAuthRequestDTO;
import com.RentEase.RentEase_backend.payloads.APIResponse;
import com.RentEase.RentEase_backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public APIResponse<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        UserDTO createdUser = this.userService.createUser(userDTO);
        return new APIResponse<UserDTO>("User Created Successfully!",
                true, HttpStatus.CREATED, createdUser);
    }

    @PostMapping("/login")
    public APIResponse<UserAuthResponseDTO> login(@Valid @RequestBody
                                                      UserAuthRequestDTO userAuthRequestDTO){


        return null;
    }

}
