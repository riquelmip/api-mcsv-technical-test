package com.microservices.auth.repository;

import com.microservices.auth.model.RoleEntity;
import com.microservices.auth.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
      List<RoleEntity> findByRoleNameIn(List<String> roleNames);
      RoleEntity findByRoleName(RoleEnum roleName);


}
