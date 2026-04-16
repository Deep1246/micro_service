package com.microservice.ecommerce.services.serviceImpl;

import com.microservice.ecommerce.dtos.ProductDto;
import com.microservice.ecommerce.entity.Product;
import com.microservice.ecommerce.repository.ProductRepo;
import com.microservice.ecommerce.services.ProductService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepo productRepo;

    private final ModelMapper modelMapper;

    private int counter=0;


    public List<ProductDto> getAllInvenotry(){


        long currentValue = System.currentTimeMillis();

//        if(currentValue % 2==0){
//            log.error("simulated more than expected no of request");
//            throw  new RuntimeException(String.format("simulation failed due inconvinent %s" ,currentValue));
//        }

        List<Product> inventories = productRepo.findAll();


        return inventories.stream().map(prod -> new ProductDto(prod.getId(),prod.getTitle(),prod.getPrice(),prod.getStock())).toList();
    }




    @Retry(name = "findProductById",fallbackMethod = "getProductByIdFallBack")
    public ProductDto getProductById(Long id){

        simulateFindFailure(id);

        Optional<Product> productByIdOpt = productRepo.findById(id);
        if(productByIdOpt.isEmpty()){

            throw new RuntimeException("Product not found");
        }
        Product product = productByIdOpt.get();
        return new ProductDto(product.getId(),product.getTitle(), product.getPrice(),product.getStock());
    }

    private ProductDto getProductByIdFallBack(Long id,Throwable ex){
        log.info("FallBack on getPRoductByIdFallback");
        log.error("InventryService :: findPRoductById failed for id {} due to {}",id,ex.getMessage());
        return new ProductDto(id,"",0.0,0);
    }

    public void     simulateFindFailure(Long id){
        if(id % 2 ==0){
            log.info("simulated find failure for id :: {}",id);
            throw new RuntimeException(String.format("Simulate find failure for id :: %s",id));
        }
    }
}
