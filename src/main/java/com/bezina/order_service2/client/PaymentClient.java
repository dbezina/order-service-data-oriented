package com.bezina.order_service2.client;

import com.bezina.order_service2.model.payment.PaymentRequest;
import com.bezina.order_service2.model.payment.PaymentStatus;

public interface PaymentClient {
    String processPayment(String customerId, String orderId, double amount);

    PaymentStatus process(PaymentRequest paymentRequest);
}
