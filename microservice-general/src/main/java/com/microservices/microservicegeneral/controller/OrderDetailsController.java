package com.microservices.microservicegeneral.controller;


import com.microservices.microservicegeneral.model.OrderDetailsEntity;
import com.microservices.microservicegeneral.service.implementation.OrderDetailsServiceImpl;
import com.microservices.microservicegeneral.util.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mcsv-general/order-details")
class OrderDetailsController {
    private static final Logger log = LoggerFactory.getLogger(OrderDetailsController.class);

    @Autowired
    private OrderDetailsServiceImpl orderDetailsService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addDetail(@RequestBody OrderDetailsEntity detail) {
        try {
            return ResponseObject.build(true, HttpStatus.CREATED, "Detail added", orderDetailsService.addDetail(detail));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/get-by-order")
    public ResponseEntity<ResponseObject> getByOrder(@RequestParam Long orderId) {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Details retrieved", orderDetailsService.findByOrderId(orderId));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }
}
