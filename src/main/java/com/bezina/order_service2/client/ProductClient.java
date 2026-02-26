package com.bezina.order_service2.client;

import com.bezina.order_service2.model.product.ProductStatus;

public interface ProductClient {
    ProductStatus getProduct(String productId);
}
