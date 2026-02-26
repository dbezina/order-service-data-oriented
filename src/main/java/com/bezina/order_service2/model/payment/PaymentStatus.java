package com.bezina.order_service2.model.payment;

import java.util.UUID;

public sealed interface PaymentStatus {
    UUID orderId();
    double amount();

    record Processed(String transactionId,
                     UUID orderId,
                     double amount) implements PaymentStatus {

    }

    record Declined(UUID orderId,
                    double amount) implements PaymentStatus {

    }
}
