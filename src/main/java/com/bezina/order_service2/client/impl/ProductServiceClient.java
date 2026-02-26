package com.bezina.order_service2.client.impl;

import com.bezina.order_service2.client.ProductClient;
import com.bezina.order_service2.exception.ApplicationException;
import com.bezina.order_service2.exception.ApplicationExceptions;
import com.bezina.order_service2.model.product.ProductStatus;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.function.Supplier;

public class ProductServiceClient extends AbstractServiceClient implements ProductClient {
    private final RestClient restClient;

    public ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public ProductStatus getProduct(String productId) {
        var errorMap = Map.<Integer, Supplier<ProductStatus>>of(
                404, ()-> ApplicationExceptions.productNotFound(productId));
        //product.service.url
        return this.executeRequest(
                () -> this.restClient.get()
                .uri("/{productId}", productId)
                .retrieve()
                .body(ProductStatus.class),
                errorMap
        );
    }

    @Override
    protected String getServiceName() {
        return "product-service";
    }
}
