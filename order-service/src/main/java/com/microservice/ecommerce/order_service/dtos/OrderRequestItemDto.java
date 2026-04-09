package com.microservice.ecommerce.order_service.dtos;

public record OrderRequestItemDto(Long id , Long productId, Integer qty) {


}
