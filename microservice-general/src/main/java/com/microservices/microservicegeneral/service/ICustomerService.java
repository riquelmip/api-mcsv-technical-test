package com.microservices.microservicegeneral.service;

import com.microservices.microservicegeneral.model.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    List<CustomerEntity> getAllCustomers();

    Optional<CustomerEntity> getCustomerById(Long id);

    CustomerEntity createCustomer(CustomerEntity customer);

    CustomerEntity updateCustomer(Long id, CustomerEntity customer);

    void deleteCustomer(Long id);

    Optional<CustomerEntity> getCustomerByUserId(Integer userId);
}
