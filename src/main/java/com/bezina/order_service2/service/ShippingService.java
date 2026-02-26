package com.bezina.order_service2.service;

import com.bezina.order_service2.model.shipping.ShippingResponse;
import com.bezina.order_service2.model.order.Order;

public interface ShippingService {
    ShippingResponse scheduleShipping(Order order);
}
