package com.microservice.ecommerce.order_service.entity;


import com.microservice.ecommerce.order_service.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status",length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Double totalPrice;

    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItems> items;
}
