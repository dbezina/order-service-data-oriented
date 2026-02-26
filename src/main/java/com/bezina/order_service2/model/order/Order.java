package com.bezina.order_service2.model.order;

import com.bezina.order_service2.model.customer.Customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record Order(UUID orderId,
                    Customer customer,
                    List<OrderItem> orderItems,
                    LocalDateTime createdAt) {
}
