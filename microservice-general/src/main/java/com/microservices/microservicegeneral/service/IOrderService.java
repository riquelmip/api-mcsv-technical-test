package com.microservices.microservicegeneral.service;

import com.microservices.microservicegeneral.dto.OrderDTO;
import com.microservices.microservicegeneral.model.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    OrderEntity create(OrderDTO order);
    Optional<OrderEntity> findById(Long id);
    List<OrderEntity> findByCustomerId(Long customerId);
    void updateStatusToPaid(Long orderId);
}