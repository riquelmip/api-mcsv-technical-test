package com.microservices.microservicegeneral.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long customerId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private boolean status;
}
