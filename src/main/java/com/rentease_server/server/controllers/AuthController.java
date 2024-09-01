package com.rentease_server.server.controllers;

import com.rentease_server.server.dtos.RefreshTokenRequestDTO;
import com.rentease_server.server.dtos.requestdtos.AuthRequestDTO;
import com.rentease_server.server.dtos.requestdtos.UserRegisterReqDTO;
import com.rentease_server.server.dtos.responsedtos.AuthResponseDTO;
import com.rentease_server.server.dtos.responsedtos.UserResponseDTO;
import com.rentease_server.server.payloads.APIResponse;
import com.rentease_server.server.security.JWTHelper;
import com.rentease_server.server.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private UserService userService;

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/register")
    public APIResponse<UserResponseDTO> createUser(@Valid @RequestBody
                                                       UserRegisterReqDTO userRegisterReqDTO){
        UserResponseDTO createdUser = this.userService.createUser(userRegisterReqDTO);
        return new APIResponse<UserResponseDTO>("User Created Successfully!",
                true, HttpStatus.CREATED, createdUser);
    }

    @PostMapping("/login")
    public AuthResponseDTO loginUser(@Valid @RequestBody AuthRequestDTO authRequestDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequestDTO.getUserName(), authRequestDTO.getPassword()
        ));

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(
                authRequestDTO.getUserName()
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
                    .user(this.modelMapper.map(userDetails, UserResponseDTO.class))
                    .accessToken(accessToken)
                    .refreshToken(refreshToken).build();
        }
        return null;
    }
}
