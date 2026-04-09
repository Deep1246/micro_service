package com.microservice.ecommerce.order_service.dtos;

import com.microservice.ecommerce.order_service.entity.OrderItems;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDto (Long id , List<OrderRequestItemDto> items, BigDecimal totalPrice){
}
