package com.bezina.order_service2.model.payment;

import java.util.UUID;

public record PaymentRequest(String customerId,
                             UUID orderId,
                             double amount) {
}
