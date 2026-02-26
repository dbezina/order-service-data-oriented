package com.bezina.order_service2.service;

import com.bezina.order_service2.model.order.OrderRequest;
import com.bezina.order_service2.model.order.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest request);
}
