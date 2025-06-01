package com.microservices.microservicegeneral.controller;


import com.microservices.microservicegeneral.model.CustomerEntity;
import com.microservices.microservicegeneral.service.implementation.CustomerServiceImpl;
import com.microservices.microservicegeneral.util.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mcsv-general/customers")
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerServiceImpl customerService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> create(@RequestBody CustomerEntity customer) {
        try {
            return ResponseObject.build(true, HttpStatus.CREATED, "Customer created successfully", customerService.createCustomer(customer));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/get-all")
    public ResponseEntity<ResponseObject> getAll() {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Customers retrieved successfully", customerService.getAllCustomers());
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/get-by-id")
    public ResponseEntity<ResponseObject> getById(@RequestParam Long id) {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Customer retrieved successfully", customerService.getCustomerById(id));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/get-by-user-id")
    public ResponseEntity<ResponseObject> getByUserId(@RequestParam Integer userId) {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Customer retrieved successfully", customerService.getCustomerByUserId(userId));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }
}
