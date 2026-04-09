package com.microservice.ecommerce.order_service.service.serviceImpl;

import com.microservice.ecommerce.order_service.dtos.OrderRequestDto;
import com.microservice.ecommerce.order_service.dtos.OrderRequestItemDto;
import com.microservice.ecommerce.order_service.entity.OrderItems;
import com.microservice.ecommerce.order_service.entity.Orders;
import com.microservice.ecommerce.order_service.repository.OrdersRepository;
import com.microservice.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrdersRepository ordersRepository;

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
}
