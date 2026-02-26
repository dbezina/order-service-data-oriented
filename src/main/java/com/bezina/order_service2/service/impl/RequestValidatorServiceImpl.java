package com.bezina.order_service2.service.impl;

import com.bezina.order_service2.client.CouponClient;
import com.bezina.order_service2.client.CustomerClient;
import com.bezina.order_service2.client.ProductClient;
import com.bezina.order_service2.exception.ApplicationExceptions;
import com.bezina.order_service2.model.coupon.Coupon;
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
    private final CouponClient couponClient;

    public RequestValidatorServiceImpl(ProductClient productClient, CustomerClient customerClient, CouponClient couponClient) {
        this.productClient = productClient;
        this.customerClient = customerClient;
        this.couponClient = couponClient;
    }


    @Override
    public Order validate(CreateOrderCommand request) {
        var product = this.getProduct(request.productId());
        var customer = this.customerClient.getCustomer(request.customerId());
        var orderItem = new OrderItem(product, request.quantity());
        System.out.println("optional string : "+ request.couponCode());
        var coupon = request.couponCode()
                .map(this.couponClient::getCoupon)
                .orElse(Coupon.none());
        System.out.println("validate coupon code: " + coupon.toString());
        return new Order(request.orderId(),customer, List.of(orderItem), LocalDateTime.now(),coupon);
    }

    private Product getProduct(String productId) {
        return switch (this.productClient.getProduct(productId)){
            case ProductStatus.Active active ->  active.product();
            case ProductStatus.Discontinued discontinued-> ApplicationExceptions.discontinuedProduct(discontinued);
        };
    }
}
