package com.RentEase.RentEase_backend.controllers;

import com.RentEase.RentEase_backend.dtos.RefreshTokenRequestDTO;
import com.RentEase.RentEase_backend.dtos.UserAuthResponseDTO;
import com.RentEase.RentEase_backend.dtos.UserDTO;
import com.RentEase.RentEase_backend.dtos.UserAuthRequestDTO;
import com.RentEase.RentEase_backend.exceptions.UserNotAuthorizedException;
import com.RentEase.RentEase_backend.payloads.APIResponse;
import com.RentEase.RentEase_backend.security.JWTHelper;
import com.RentEase.RentEase_backend.services.UserService;
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
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/register")
    public APIResponse<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        UserDTO createdUser = this.userService.createUser(userDTO);
        return new APIResponse<UserDTO>("User Created Successfully!",
                true, HttpStatus.CREATED, createdUser);
    }

    @PostMapping("/login")
    public UserAuthResponseDTO login(@Valid @RequestBody
                                                      UserAuthRequestDTO userAuthRequestDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userAuthRequestDTO.getUserName(), userAuthRequestDTO.getPassword()
        ));

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(
                userAuthRequestDTO.getUserName()
        );
        String accessToken = this.jwtHelper.generateToken(userDetails);
        String refreshToken = this.jwtHelper.generateRefreshToken(new HashMap<>(),userDetails);
        return UserAuthResponseDTO.builder()
                .message("User Logged successfully!")
                .success(true)
                .status(HttpStatus.OK)
                .user(this.modelMapper.map(userDetails, UserDTO.class))
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
    }

    @PostMapping("/refresh")
    public UserAuthResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        String userName = jwtHelper.extractUserName(refreshTokenRequestDTO.getToken());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(
                userName
        );
        logger.info("Username: " + userDetails.getUsername() + "Role: "
                + userDetails.getAuthorities());
        if(jwtHelper.isTokenValid(refreshTokenRequestDTO.getToken(), userDetails)){
            String accessToken = this.jwtHelper.generateToken(userDetails);
            String refreshToken = this.jwtHelper.generateRefreshToken(new HashMap<>(),userDetails);
            logger.info("NEW TOKEN GENERATED");
            return UserAuthResponseDTO.builder()
                    .message("User Logged successfully!")
                    .success(true)
                    .status(HttpStatus.OK)
                    .user(this.modelMapper.map(userDetails, UserDTO.class))
                    .accessToken(accessToken)
                    .refreshToken(refreshToken).build();
        }else {
            throw new UserNotAuthorizedException("Invalid Token!");
        }
    }
}
