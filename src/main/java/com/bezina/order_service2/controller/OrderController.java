package com.bezina.order_service2.controller;

import com.bezina.order_service2.model.order.OrderRequest;
import com.bezina.order_service2.model.order.OrderResponse;
import com.bezina.order_service2.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("orders")
    public OrderResponse placeOrder( @Validated @RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }
}
