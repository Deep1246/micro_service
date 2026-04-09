package com.microservice.ecommerce.order_service.service;

import com.microservice.ecommerce.order_service.dtos.OrderRequestDto;

import java.util.List;

public interface OrderService {

    public List<OrderRequestDto> getAllOrders();

    public OrderRequestDto getOrderById(Long id);
}
