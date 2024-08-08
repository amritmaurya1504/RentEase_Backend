package com.RentEase.RentEase_backend.controllers;

import com.RentEase.RentEase_backend.dtos.UserDTO;
import com.RentEase.RentEase_backend.dtos.UserUpdateDTO;
import com.RentEase.RentEase_backend.payloads.APIResponse;
import com.RentEase.RentEase_backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public APIResponse<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        UserDTO createdUser = this.userService.createUser(userDTO);
        return new APIResponse<UserDTO>("User Created Successfully!",
                true, HttpStatus.CREATED, createdUser);
    }

    @GetMapping
    public APIResponse<List<UserDTO>> getAllUsers(){
        List<UserDTO> allUsers = this.userService.getAllUsers();
        return new APIResponse<List<UserDTO>>("All Users Fetched Successfully!",
                true, HttpStatus.OK, allUsers);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable String userId){
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    @DeleteMapping("/{userId}")
    public APIResponse<UserDTO> deleteSingleUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return new APIResponse<>("User Deleted Successfully!",
                true, HttpStatus.OK, null);
    }

    @PutMapping("/{userId}")
    public APIResponse<UserDTO> updateUser(@PathVariable String userId, @Valid @RequestBody
                                           UserUpdateDTO userUpdateDTO){
        return new APIResponse<>("User Updated Successfully!",
                true, HttpStatus.CREATED, this.userService
                .updateUser(userId, userUpdateDTO));
    }

}
