package com.microservices.microservicegeneral.service.implementation;

import com.microservices.microservicegeneral.model.OrderDetailsEntity;
import com.microservices.microservicegeneral.repository.OrderDetailsRepository;
import com.microservices.microservicegeneral.service.IOrderDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsServiceImpl implements IOrderDetailsService {

    private final com.microservices.microservicegeneral.repository.OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    public List<OrderDetailsEntity> findByOrderId(Long orderId) {
        return orderDetailsRepository.findByOrderId(orderId);
    }

    @Override
    public OrderDetailsEntity addDetail(OrderDetailsEntity detail) {
        return orderDetailsRepository.save(detail);
    }

    @Override
    public void delete(Long id) {
        orderDetailsRepository.deleteById(id);
    }
}