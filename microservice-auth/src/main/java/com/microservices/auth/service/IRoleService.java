package com.microservices.auth.service;



import com.microservices.auth.model.RoleEntity;
import com.microservices.auth.model.UserEntity;
import com.microservices.auth.model.dto.AuthCreateUserRequestDTO;

import java.util.List;

public interface IRoleService {
    List<RoleEntity> getAllRoles();
}
