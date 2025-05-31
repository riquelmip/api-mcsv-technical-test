package com.microservices.microservicegeneral.service.implementation;


import com.microservices.microservicegeneral.model.PaymentEntity;
import com.microservices.microservicegeneral.repository.PaymentRepository;
import com.microservices.microservicegeneral.service.IPaymentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentEntity create(PaymentEntity payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<PaymentEntity> findByOrderId(Long orderId) {
        return Optional.ofNullable(paymentRepository.findByOrderId(orderId));
    }
}
