package com.microservices.microservicegeneral.service;


import com.microservices.microservicegeneral.model.AddressEntity;

import java.util.List;
import java.util.Optional;

public interface IAddressService {
    List<AddressEntity> findByCustomerId(Long customerId);
    AddressEntity create(AddressEntity address);
    AddressEntity update(Long id, AddressEntity address);
    void delete(Long id);
    Optional<AddressEntity> findById(Long id);
}
