package com.microservice.ecommerce.order_service.repository;

import com.microservice.ecommerce.order_service.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItems,Long> {
}
