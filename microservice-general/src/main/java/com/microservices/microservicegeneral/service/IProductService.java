package com.microservices.microservicegeneral.service;


import com.microservices.microservicegeneral.dto.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
}