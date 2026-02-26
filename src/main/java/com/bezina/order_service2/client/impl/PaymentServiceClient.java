package com.bezina.order_service2.client.impl;

import com.bezina.order_service2.client.PaymentClient;
import com.bezina.order_service2.model.payment.PaymentRequest;
import com.bezina.order_service2.model.payment.PaymentStatus;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.function.Supplier;

public class PaymentServiceClient extends AbstractServiceClient implements PaymentClient {
    private final RestClient restClient;

    public PaymentServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    protected String getServiceName() {
        return "payment-service";
    }

    @Override
    protected <T> T executeRequest(Supplier<T> supplier, Map<Integer, Supplier<T>> errorMap) {
        return super.executeRequest(supplier, errorMap);
    }

    @Override
    public String processPayment(String customerId, String orderId, double amount) {
        return "";
    }

    @Override
    public PaymentStatus process(PaymentRequest paymentRequest) {
        var errorMap = Map.<Integer,Supplier<PaymentStatus>>of(
                402, () -> new PaymentStatus.Declined(
                        paymentRequest.orderId(),
                        paymentRequest.amount())
        );
        return this.executeRequest(
                ()-> this.restClient.post()
                        .uri("/process")
                        .body(paymentRequest)
                        .retrieve()
                        .body(PaymentStatus.Processed.class)
                ,errorMap);
    }
}
