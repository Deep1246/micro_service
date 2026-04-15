package com.microservice.ecommerce.order_service.service.serviceImpl;

import com.microservice.ecommerce.order_service.dtos.OrderRequestDto;
import com.microservice.ecommerce.order_service.dtos.OrderRequestItemDto;
import com.microservice.ecommerce.order_service.entity.OrderItems;
import com.microservice.ecommerce.order_service.entity.Orders;
import com.microservice.ecommerce.order_service.repository.OrdersRepository;
import com.microservice.ecommerce.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @Override
    public List<OrderRequestDto> getAllOrders() {

        log.info("fetching all orders");
        List<Orders> orderList = ordersRepository.findAll();

        List<OrderRequestItemDto> list = orderList.stream()
                .flatMap(ord -> ord.getItems().stream().map(this::mapItemToDto)).toList();

        return orderList.stream().map(ord -> new OrderRequestDto(ord.getId(),list,new BigDecimal(ord.getTotalPrice()))).toList();
    }

    private OrderRequestItemDto mapItemToDto(OrderItems item) {
        return new OrderRequestItemDto(
                item.getId(),
                item.getProductId(),
                item.getQty()
        );
    }

    @Override
    public OrderRequestDto getOrderById(Long id) {

        log.info("Fetching  order with ID :: {}",id);

        Optional<Orders> orderOpt = ordersRepository.findById(id);

        if(orderOpt.isEmpty())
            throw new RuntimeException("order not found y given id :: "+id);

        Orders orders = orderOpt.get();

        List<OrderRequestItemDto> list = orders.getItems().stream().map(this::mapItemToDto).toList();
        return new OrderRequestDto(orders.getId(),list,new BigDecimal(orders.getTotalPrice()));

    }

    @CircuitBreaker(name = "findInventories", fallbackMethod = "findInventoriesFallBack")
    @Override
    public Map findInventories() {


        ServiceInstance inventoryService = discoveryClient.getInstances("inventory-service").getFirst();

        System.out.println(inventoryService.getUri());
        String body = restClient
                .get()
                .uri(inventoryService.getUri().resolve("/api/v1/products"))
                .retrieve()
                .body(String.class);

        ObjectMapper obj = new ObjectMapper();

        Map map = obj.convertValue(body, Map.class);

        return map;

    }

    public Map findInventoriesFallBack(Throwable ex){


        log.info("Fallback :: findInventoriesFallBack Method invoke");

        log.error("Inventories service failed ::  findInventories error :: {}",ex.getMessage());



        return Collections.emptyMap();
    }
}
