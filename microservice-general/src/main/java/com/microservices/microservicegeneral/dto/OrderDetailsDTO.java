package com.microservices.microservicegeneral.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailsDTO {
    private Long productId;
        private String productName;
        private BigDecimal unitPrice;
        private Integer quantity;
        private BigDecimal subtotal;
}
