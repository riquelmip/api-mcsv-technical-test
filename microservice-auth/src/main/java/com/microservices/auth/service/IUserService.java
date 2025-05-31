package com.microservices.auth.service;



import com.microservices.auth.model.UserEntity;
import com.microservices.auth.model.dto.AuthCreateUserRequestDTO;

import java.util.List;

public interface IUserService {
    UserEntity createUser(AuthCreateUserRequestDTO userEntity);

    List<UserEntity> getAllUsers();

    UserEntity getUserById(Long userId);

    boolean deleteUserById(Long userId);

    UserEntity getUserByUsername(String username);

    UserEntity getUserByEmail(String email);

}
