package com.microservices.microservicegeneral.repository;

import com.microservices.microservicegeneral.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    List<AddressEntity> findByCustomerId(Long customerId);
}