package com.microservice.ecommerce.order_service.repository;

import com.microservice.ecommerce.order_service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class OrdersRepository implements JpaRepository<Orders,Long> {
}
