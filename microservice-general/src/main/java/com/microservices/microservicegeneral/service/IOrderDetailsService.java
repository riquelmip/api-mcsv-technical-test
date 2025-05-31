package com.microservices.microservicegeneral.service;


import com.microservices.microservicegeneral.model.OrderDetailsEntity;

import java.util.List;

public interface IOrderDetailsService {
    List<OrderDetailsEntity> findByOrderId(Long orderId);
    OrderDetailsEntity addDetail(OrderDetailsEntity detail);
    void delete(Long id);
}