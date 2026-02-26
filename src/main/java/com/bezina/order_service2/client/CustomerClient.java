package com.bezina.order_service2.client;

import com.bezina.order_service2.model.customer.Customer;

public interface CustomerClient {
    Customer getCustomer(String customerId);
}
