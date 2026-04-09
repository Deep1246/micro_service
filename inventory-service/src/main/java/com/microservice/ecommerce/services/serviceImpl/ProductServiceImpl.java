package com.microservice.ecommerce.services.serviceImpl;

import com.microservice.ecommerce.dtos.ProductDto;
import com.microservice.ecommerce.entity.Product;
import com.microservice.ecommerce.repository.ProductRepo;
import com.microservice.ecommerce.services.ProductService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepo productRepo;

    private final ModelMapper modelMapper;


    public List<ProductDto> getAllInvenotry(){
        List<Product> inventories = productRepo.findAll();
        return inventories.stream().map(prod -> new ProductDto(prod.getId(),prod.getTitle(),prod.getPrice(),prod.getStock())).toList();
    }

    public ProductDto getProductById(Long id){
        Optional<Product> productByIdOpt = productRepo.findById(id);
        if(productByIdOpt.isEmpty()){
            throw new RuntimeException("Product not found");
        }
        Product product = productByIdOpt.get();
        return new ProductDto(product.getId(),product.getTitle(), product.getPrice(),product.getStock());
    }
}
