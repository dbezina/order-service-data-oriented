package com.bezina.order_service2.model.order;

import java.util.UUID;
//do not need any validation because it is in OrderRequest
public record CreateOrderCommand(UUID orderId,
                                 String customerId,
                                 String productId,
                                 int quantity) {
    public static CreateOrderCommand create (String customerId, String productId, int quantity) {
        return new CreateOrderCommand(
                UUID.randomUUID(),
                customerId,
                productId,
                quantity);
    }
}
