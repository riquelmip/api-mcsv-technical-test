package com.microservices.auth.controller;

import com.microservices.auth.model.UserEntity;
import com.microservices.auth.model.dto.AuthCreateUserRequestDTO;
import com.microservices.auth.service.implementation.UserServiceImpl;
import com.microservices.auth.util.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mcsv-auth/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createUser(@RequestBody AuthCreateUserRequestDTO user) {
        try {
            UserEntity userCreated = userService.createUser(user);
            return ResponseObject.build(true, HttpStatus.CREATED, "User created successfully", userCreated);
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/get-all")
    public ResponseEntity<ResponseObject> getAllUsers() {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Users retrieved successfully", userService.getAllUsers());
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/get-by-username")
    public ResponseEntity<ResponseObject> getUserByUsername(@RequestParam String username) {
        try {
            UserEntity user = userService.getUserByUsername(username);
            user.setPassword(null);
            return ResponseObject.build(true, HttpStatus.OK, "User retrieved successfully", user);
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

}
