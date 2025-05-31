package com.microservices.microservicegeneral.service;

import com.microservices.microservicegeneral.model.PaymentEntity;

import java.util.Optional;

public interface IPaymentService {
    PaymentEntity create(PaymentEntity payment);
    Optional<PaymentEntity> findByOrderId(Long orderId);
}