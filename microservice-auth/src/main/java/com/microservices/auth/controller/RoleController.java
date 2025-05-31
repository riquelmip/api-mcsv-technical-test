package com.microservices.auth.controller;

import com.microservices.auth.model.UserEntity;
import com.microservices.auth.model.dto.AuthCreateUserRequestDTO;
import com.microservices.auth.service.implementation.RoleServiceImpl;
import com.microservices.auth.service.implementation.UserServiceImpl;
import com.microservices.auth.util.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mcsv-auth/roles")
public class RoleController {
    private static final Logger log = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private RoleServiceImpl roleService;

    @PostMapping("/get-all")
    public ResponseEntity<ResponseObject> getAllRoles() {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Roles retrieved successfully", roleService.getAllRoles());
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

}
