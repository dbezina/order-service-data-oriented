package com.bezina.order_service2.service;

import com.bezina.order_service2.model.order.CreateOrderCommand;
import com.bezina.order_service2.model.order.Order;

public interface RequestValidatorService {
    Order validate(CreateOrderCommand request);

}
