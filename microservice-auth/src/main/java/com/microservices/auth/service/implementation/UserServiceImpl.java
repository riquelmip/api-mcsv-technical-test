package com.microservices.auth.service.implementation;


import com.microservices.auth.model.ProviderEnum;
import com.microservices.auth.model.RoleEntity;
import com.microservices.auth.model.UserEntity;
import com.microservices.auth.model.dto.AuthCreateUserRequestDTO;
import com.microservices.auth.repository.RoleRepository;
import com.microservices.auth.repository.UserRepository;
import com.microservices.auth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserEntity createUser(AuthCreateUserRequestDTO authCreateUserRequestDTO) {
        String username = authCreateUserRequestDTO.username();
        String password = authCreateUserRequestDTO.password();
        List<String> rolesRequest = authCreateUserRequestDTO.roles().list();

        Set<RoleEntity> roleEntityList = roleRepository.findByRoleNameIn(rolesRequest).stream().collect(Collectors.toSet());

        if (roleEntityList.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist.");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roleEntityList)
                .isEnable(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .name(authCreateUserRequestDTO.name() != null ? authCreateUserRequestDTO.name() : null)
                .email(authCreateUserRequestDTO.email() != null ? authCreateUserRequestDTO.email() : null)
                .isOAuth2(authCreateUserRequestDTO.isOAuth2())
                .provider(authCreateUserRequestDTO.provider() != null ? authCreateUserRequestDTO.provider() : ProviderEnum.LOCAL)
                .build();

        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        users.forEach(user -> user.setPassword(null));
        return users;
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public boolean deleteUserById(Long userId) {
        boolean isDeleted = false;
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            isDeleted = true;
        }
        return isDeleted;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findUserEntityByUsername(username).orElse(null);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findUserEntityByEmail(email).orElse(null);
    }


}
