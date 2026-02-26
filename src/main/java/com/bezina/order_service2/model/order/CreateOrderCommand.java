package com.bezina.order_service2.model.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;
import java.util.UUID;
//do not need any validation because it is in OrderRequest
public record CreateOrderCommand(UUID orderId,
                                 String customerId,
                                 String productId,
                                 int quantity,
                                 Optional<String> couponCode) {
    public static CreateOrderCommand create( String customerId, String productId, int quantity, String couponCode) {
       return new CreateOrderCommand(
               UUID.randomUUID(),
               customerId,
               productId,
               quantity,
               Optional.ofNullable(couponCode)
       );
    }

}
