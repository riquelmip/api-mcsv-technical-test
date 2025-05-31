package com.microservices.auth.service.implementation;


import com.microservices.auth.model.ProviderEnum;
import com.microservices.auth.model.RoleEntity;
import com.microservices.auth.model.UserEntity;
import com.microservices.auth.model.dto.AuthCreateUserRequestDTO;
import com.microservices.auth.repository.RoleRepository;
import com.microservices.auth.repository.UserRepository;
import com.microservices.auth.service.IRoleService;
import com.microservices.auth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleEntity> getAllRoles() {
        return roleRepository.findAll();
    }
}
