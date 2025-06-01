package com.microservices.microservicegeneral.service.implementation;


import com.microservices.microservicegeneral.model.*;
import com.microservices.microservicegeneral.repository.PaymentRepository;
import com.microservices.microservicegeneral.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private OrderDetailsServiceImpl orderDetailsService;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentEntity create(Long orderId) {
        OrderEntity order = orderService.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Fetch order details to calculate the total
        List<OrderDetailsEntity> orderDetails = orderDetailsService.findByOrderId(orderId);
        if (orderDetails.isEmpty()) {
            throw new RuntimeException("No order details found for order id: " + orderId);
        }

        // Calculate total amount
        BigDecimal totalAmount = orderDetails.stream()
                .map(OrderDetailsEntity::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        PaymentEntity payment = new PaymentEntity();
        payment.setOrder(order);
        payment.setAmount(totalAmount);
        payment.setPaymentStatus(PaymentStatusEnum.SUCCESS);
        payment.setPaymentMethod("CASH");

        paymentRepository.save(payment);

        // Update order status to PAID
        orderService.updateStatusToPaid(orderId);
        return payment;
    }

    @Override
    public Optional<PaymentEntity> findByOrderId(Long orderId) {
        return Optional.ofNullable(paymentRepository.findByOrderId(orderId));
    }
}
