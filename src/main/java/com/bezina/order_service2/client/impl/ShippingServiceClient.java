package com.bezina.order_service2.client.impl;

import com.bezina.order_service2.client.ShippingClient;
import com.bezina.order_service2.model.shipping.ShippingRequest;
import com.bezina.order_service2.model.shipping.ShippingResponse;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class ShippingServiceClient extends AbstractServiceClient implements ShippingClient {
    private final RestClient restClient;

    public ShippingServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }
    @Override
    protected String getServiceName() {
        return "shipping-service";
    }
    @Override
    public ShippingResponse scheduleShipping(ShippingRequest shippingRequest) {
        return this.executeRequest(
                ()->this.restClient.post()
                        .uri("/schedule")
                        .body(shippingRequest)
                        .retrieve()
                        .body(ShippingResponse.class),
                Collections.emptyMap()
        );
    }
}
