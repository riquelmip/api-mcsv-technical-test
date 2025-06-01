package com.microservices.microservicegeneral.service.implementation;

import com.microservices.microservicegeneral.dto.OrderDTO;
import com.microservices.microservicegeneral.model.*;
import com.microservices.microservicegeneral.repository.OrderRepository;
import com.microservices.microservicegeneral.service.IOrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private AddressServiceImpl addressService;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderEntity create(OrderDTO order) {
        // Create the OrderEntity
        OrderEntity orderEntity = new OrderEntity();
        CustomerEntity customer = customerService.getCustomerById(order.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with userId: " + order.getCustomerId()));
        orderEntity.setCustomer(customer);
        AddressEntity deliveryAddress = addressService.findById(order.getDeliveryAddressId())
                .orElseThrow(() -> new EntityNotFoundException("Delivery address not found with id: " + order.getDeliveryAddressId()));
        orderEntity.setDeliveryAddress(deliveryAddress);
        orderEntity.setStatus(OrderStatusEnum.CREATED);

        // Map OrderDetailsDTO to OrderDetailsEntity
        List<OrderDetailsEntity> orderDetailsEntities = order.getOrderDetails().stream().map(detail -> {
            OrderDetailsEntity detailsEntity = new OrderDetailsEntity();
            detailsEntity.setProductId(detail.getProductId());
            detailsEntity.setProductName(detail.getProductName());
            detailsEntity.setUnitPrice(detail.getUnitPrice());
            detailsEntity.setQuantity(detail.getQuantity());
            detailsEntity.setSubtotal(detail.getSubtotal());
            detailsEntity.setOrder(orderEntity);
            return detailsEntity;
        }).toList();

        // Set the order details in the OrderEntity
        orderEntity.setOrderDetails(orderDetailsEntities);

        // Save the OrderEntity (including details) in the repository
        return orderRepository.save(orderEntity);
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
    public void updateStatusToPaid(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
        order.setStatus(OrderStatusEnum.PAID);
        orderRepository.save(order);
    }
}
