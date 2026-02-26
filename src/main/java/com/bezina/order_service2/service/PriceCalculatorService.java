package com.bezina.order_service2.service;

import com.bezina.order_service2.model.PriceSummary;
import com.bezina.order_service2.model.coupon.Coupon;
import com.bezina.order_service2.model.order.Order;

public interface PriceCalculatorService {
    PriceSummary calculate(Order order);
}
