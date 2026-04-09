package com.microservice.ecommerce.services;

import com.microservice.ecommerce.dtos.ProductDto;
import com.microservice.ecommerce.entity.Product;
import com.microservice.ecommerce.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ProductService {
    public List<ProductDto> getAllInvenotry();
    public ProductDto getProductById(Long id);
}
