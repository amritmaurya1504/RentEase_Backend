package com.RentEase.RentEase_backend.controllers;

import com.RentEase.RentEase_backend.dtos.RefreshTokenRequestDTO;
import com.RentEase.RentEase_backend.dtos.requestdtos.AuthRequestDTO;
import com.RentEase.RentEase_backend.dtos.requestdtos.UserRegisterReqDTO;
import com.RentEase.RentEase_backend.dtos.responsedtos.AuthResponseDTO;
import com.RentEase.RentEase_backend.dtos.responsedtos.UserResponseDTO;
import com.RentEase.RentEase_backend.payloads.APIResponse;
import com.RentEase.RentEase_backend.security.JWTHelper;
import com.RentEase.RentEase_backend.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Controller", description = "Authentication related operations, accessible publicly")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;


    @PostMapping("/register")
    public APIResponse<UserResponseDTO> createUser(@Valid @RequestBody UserRegisterReqDTO userRegisterReqDTO){
        UserResponseDTO createdUser = this.userService.createUser(userRegisterReqDTO);
        return new APIResponse<UserResponseDTO>("User Created Successfully!",
                true, HttpStatus.CREATED, createdUser);
    }


    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody
                                 AuthRequestDTO userAuthRequestDTO){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userAuthRequestDTO.getUserName(), userAuthRequestDTO.getPassword()
        ));


        UserDetails userDetails = this.userDetailsService.loadUserByUsername(
                userAuthRequestDTO.getUserName()
        );
        String accessToken = this.jwtHelper.generateToken(userDetails);
        String refreshToken = this.jwtHelper.generateRefreshToken(new HashMap<>(),userDetails);
        return AuthResponseDTO.builder()
                .message("User Logged successfully!")
                .success(true)
                .status(HttpStatus.OK)
                .user(this.modelMapper.map(userDetails, UserResponseDTO.class))
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
    }

    @PostMapping("/refresh")
    public AuthResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        String userName = jwtHelper.extractUserName(refreshTokenRequestDTO.getToken());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(
                userName
        );

        if(jwtHelper.isTokenValid(refreshTokenRequestDTO.getToken(), userDetails)){
            String accessToken = this.jwtHelper.generateToken(userDetails);
            String refreshToken = this.jwtHelper.generateRefreshToken(new HashMap<>(),userDetails);
            return AuthResponseDTO.builder()
                    .message("Token Refreshed successfully!")
                    .success(true)
                    .status(HttpStatus.OK)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken).build();
        }
        return null;
    }

}
