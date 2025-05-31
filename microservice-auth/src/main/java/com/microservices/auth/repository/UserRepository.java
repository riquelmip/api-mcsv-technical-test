package com.microservices.auth.repository;

import com.microservices.auth.model.ProviderEnum;
import com.microservices.auth.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);

    Optional<UserEntity> findUserEntityByUsernameAndProvider(String username, ProviderEnum provider);

    Optional<UserEntity> findUserEntityByEmail(String email);
}
