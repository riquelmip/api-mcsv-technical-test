package com.microservices.microservicegeneral.controller;


import com.microservices.microservicegeneral.dto.AddressDTO;
import com.microservices.microservicegeneral.model.AddressEntity;
import com.microservices.microservicegeneral.model.CustomerEntity;
import com.microservices.microservicegeneral.repository.CustomerRepository;
import com.microservices.microservicegeneral.service.implementation.AddressServiceImpl;
import com.microservices.microservicegeneral.util.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mcsv-general/addresses")
class AddressController {
    private static final Logger log = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressServiceImpl addressService;
    @Autowired
    private CustomerRepository customerRepository;

 @PostMapping("/create")
public ResponseEntity<ResponseObject> create(@RequestBody AddressDTO dto) {
    try {
        CustomerEntity customer = customerRepository.findById(dto.getCustomerId())
            .orElseThrow(() -> new RuntimeException("Customer not found"));

        AddressEntity address = AddressEntity.builder()
            .customer(customer)
            .street(dto.getStreet())
            .city(dto.getCity())
            .state(dto.getState())
            .postalCode(dto.getPostalCode())
            .country(dto.getCountry())
            .status(dto.isStatus())
            .build();

        AddressEntity created = addressService.create(address);
        return ResponseObject.build(true, HttpStatus.CREATED, "Address created successfully", created);
    } catch (Exception e) {
        return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }
}


    @PostMapping("/get-by-customer")
    public ResponseEntity<ResponseObject> getByCustomer(@RequestParam Long customerId) {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Addresses retrieved", addressService.findByCustomerId(customerId));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }
}
