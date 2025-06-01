package com.microservices.microservicegeneral.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private Long customerId;
    private Long deliveryAddressId;
    private List<OrderDetailsDTO> orderDetails;
}

