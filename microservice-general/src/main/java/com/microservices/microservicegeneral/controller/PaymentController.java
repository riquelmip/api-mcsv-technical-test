package com.microservices.microservicegeneral.controller;

import com.microservices.microservicegeneral.model.PaymentEntity;
import com.microservices.microservicegeneral.service.implementation.PaymentServiceImpl;
import com.microservices.microservicegeneral.util.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mcsv-general/payments")
class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentServiceImpl paymentService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> create(@RequestParam Long orderId) {
        try {
            return ResponseObject.build(true, HttpStatus.CREATED, "Payment registered", paymentService.create(orderId));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/get-by-order")
    public ResponseEntity<ResponseObject> getByOrder(@RequestParam Long orderId) {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Payment retrieved", paymentService.findByOrderId(orderId));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }
}
