package com.bezina.order_service2.exception;

import java.util.List;
import java.util.UUID;
public sealed interface DomainError extends ApplicationError {
    enum Entity {
        CUSTOMER,
        PRODUCT
    }
    record EntityNotFound ( Entity entity, String Id) implements DomainError {
    }
    record ProductDiscontinued (String productId,
                                  List<String> recommendedProducts) implements DomainError {
    }
    record PaymentIsDeclined (UUID orderId,
                              double amount) implements DomainError {
    }
}
