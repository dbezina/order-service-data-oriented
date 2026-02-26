package com.bezina.order_service2.client.impl;

import com.bezina.order_service2.client.CustomerClient;
import com.bezina.order_service2.client.ProductClient;
import com.bezina.order_service2.exception.ApplicationExceptions;
import com.bezina.order_service2.model.customer.Customer;
import com.bezina.order_service2.model.product.ProductStatus;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.function.Supplier;

public class CustomerServiceClient extends AbstractServiceClient implements CustomerClient {
    private final RestClient restClient;

    public CustomerServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    protected String getServiceName() {
        return "customer-service";
    }

    @Override
    public Customer getCustomer(String customerId) {
        var errorMap = Map.<Integer, Supplier<Customer>>of(
                404, ()-> ApplicationExceptions.customerNotFound(customerId));
        //product.service.url
        return this.executeRequest(
                () -> this.restClient.get()
                        .uri("/{customerId}", customerId)
                        .retrieve()
                        .body(Customer.class),
                errorMap
        );
    }
}
