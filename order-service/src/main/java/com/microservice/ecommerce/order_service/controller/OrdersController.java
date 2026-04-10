package com.microservice.ecommerce.order_service.controller;

import com.microservice.ecommerce.order_service.dtos.OrderRequestDto;
import com.microservice.ecommerce.order_service.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderService orderService;

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @GetMapping
    public ResponseEntity<?> getAllOrder(){

        List<OrderRequestDto> allOrders = orderService.getAllOrders();

        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable  Long id){

        OrderRequestDto orderById = orderService.getOrderById(id);

        return ResponseEntity.ok(orderById);
    }


    @GetMapping("/inventories")
    public ResponseEntity<?> fetchAllInventory(){


        ServiceInstance inventoryService = discoveryClient.getInstances("inventory-service").getFirst();

        System.out.println(inventoryService.getUri());
        String body = restClient
                .get()
                .uri(inventoryService.getUri().resolve("/api/v1/products"))
                .retrieve()
                .body(String.class);


        return ResponseEntity.ok(body);
    }




}
