package com.microservices.microservicegeneral.service.implementation;

import com.microservices.microservicegeneral.model.OrderEntity;
import com.microservices.microservicegeneral.model.OrderStatusEnum;
import com.microservices.microservicegeneral.repository.OrderRepository;
import com.microservices.microservicegeneral.service.IOrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderEntity create(OrderEntity order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<OrderEntity> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<OrderEntity> findByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public void updateStatus(Long orderId, String status) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatus(OrderStatusEnum.valueOf(status));
        orderRepository.save(order);
    }
}
