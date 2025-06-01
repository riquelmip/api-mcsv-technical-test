package com.microservices.microservicegeneral.controller;


import com.microservices.microservicegeneral.dto.AddressDTO;
import com.microservices.microservicegeneral.model.AddressEntity;
import com.microservices.microservicegeneral.model.CustomerEntity;
import com.microservices.microservicegeneral.repository.CustomerRepository;
import com.microservices.microservicegeneral.service.implementation.AddressServiceImpl;
import com.microservices.microservicegeneral.service.implementation.ProductServiceImpl;
import com.microservices.microservicegeneral.util.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mcsv-general/products")
class ProductsController {
    private static final Logger log = LoggerFactory.getLogger(ProductsController.class);

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping("/get-all")
    public ResponseEntity<ResponseObject> getAllProducts() {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Products retrieved successfully", productService.getAllProducts());
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/get-by-id")
    public ResponseEntity<ResponseObject> getProductById(@RequestParam Long id) {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Product retrieved successfully", productService.getProductById(id));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }
}
