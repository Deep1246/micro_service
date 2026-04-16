package com.microservice.ecommerce.order_service.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "inventory-service")
public interface InventoryFeingClient {


    @GetMapping("/")




}
