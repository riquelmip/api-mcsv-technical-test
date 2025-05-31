package com.microservices.microservicegeneral.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
    private RatingDTO rating;
}