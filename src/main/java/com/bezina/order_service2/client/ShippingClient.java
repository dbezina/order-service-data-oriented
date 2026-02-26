package com.bezina.order_service2.client;

import com.bezina.order_service2.model.shipping.ShippingRequest;
import com.bezina.order_service2.model.shipping.ShippingResponse;

public interface ShippingClient {
    ShippingResponse scheduleShipping(ShippingRequest shippingRequest);
}
