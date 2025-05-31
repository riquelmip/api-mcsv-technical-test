package com.microservices.microservicegeneral.service.implementation;


import com.microservices.microservicegeneral.dto.ProductDTO;
import com.microservices.microservicegeneral.service.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    private final String BASE_URL = "https://fakestoreapi.com/products";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<ProductDTO> getAllProducts() {
        ProductDTO[] products = restTemplate.getForObject(BASE_URL, ProductDTO[].class);
        return Arrays.asList(products);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, ProductDTO.class);
    }
}