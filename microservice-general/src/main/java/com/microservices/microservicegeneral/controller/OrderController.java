package com.microservices.microservicegeneral.controller;


import com.microservices.microservicegeneral.dto.OrderDTO;
import com.microservices.microservicegeneral.model.OrderEntity;
import com.microservices.microservicegeneral.service.implementation.OrderServiceImpl;
import com.microservices.microservicegeneral.util.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mcsv-general/orders")
class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> create(@RequestBody OrderDTO order) {
        try {
            return ResponseObject.build(true, HttpStatus.CREATED, "Order created successfully", orderService.create(order));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/get-by-customer")
    public ResponseEntity<ResponseObject> getByCustomer(@RequestParam Long customerId) {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Orders retrieved", orderService.findByCustomerId(customerId));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }
}
