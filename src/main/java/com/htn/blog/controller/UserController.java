package com.htn.blog.controller;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.dto.UserDTO;
import com.htn.blog.entity.User;
import com.htn.blog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Get all user rest api")
    @GetMapping()
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> getAllUser(){
        List<User> users = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Get all user successfully!")
                        .data(users)
                        .build()
        );
    }
    @Operation(summary = "Get user By tag id rest api")
    @GetMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> getUserInfo(@PathVariable("id") Long userId){
        User user = userService.getUserInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Get user by id successfully!")
                        .data(user)
                        .build()
        );
    }
    @Operation(summary = "Check user with email rest api")
    @GetMapping("/checkEmail/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable("email") String email){
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Get user by id successfully!")
                        .data(userService.existsEmail(email))
                        .build()
        );
    }
    @Operation(summary = "Add new a user rest api")
    @PostMapping()
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> addUser(UserDTO userDTO){
        User user = userService.addUser(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Get user by id successfully!")
                        .data(user)
                        .build()
        );
    }
    @Operation(summary = "Update user rest api")
    @PutMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long userId, @Valid @RequestBody UserDTO userDTO){
        User user = userService.updateUser(userId, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Get user by id successfully!")
                        .data(user)
                        .build()
        );
    }
    @Operation(summary = "Update user rest api")
    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Get user by id successfully!")
                        .data("")
                        .build()
        );
    }
}
