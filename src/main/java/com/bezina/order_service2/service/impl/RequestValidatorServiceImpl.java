package com.bezina.order_service2.service.impl;

import com.bezina.order_service2.client.CustomerClient;
import com.bezina.order_service2.client.ProductClient;
import com.bezina.order_service2.exception.ApplicationExceptions;
import com.bezina.order_service2.model.order.CreateOrderCommand;
import com.bezina.order_service2.model.order.Order;
import com.bezina.order_service2.model.order.OrderItem;
import com.bezina.order_service2.model.product.Product;
import com.bezina.order_service2.model.product.ProductStatus;
import com.bezina.order_service2.service.RequestValidatorService;

import java.time.LocalDateTime;
import java.util.List;

public class RequestValidatorServiceImpl implements RequestValidatorService {
    private final ProductClient productClient;
    private final CustomerClient  customerClient;

    public RequestValidatorServiceImpl(ProductClient productClient, CustomerClient customerClient) {
        this.productClient = productClient;
        this.customerClient = customerClient;
    }

    @Override
    public Order validate(CreateOrderCommand request) {
        var product = this.getProduct(request.productId());
        var customer = this.customerClient.getCustomer(request.customerId());
        var orderItem = new OrderItem(product, request.quantity());

        return new Order(request.orderId(),customer, List.of(orderItem), LocalDateTime.now());
    }
    private Product getProduct(String productId) {
        return switch (this.productClient.getProduct(productId)){
            case ProductStatus.Active active ->  active.product();
            case ProductStatus.Discontinued discontinued-> ApplicationExceptions.discontinuedProduct(discontinued);
        };
    }
}
