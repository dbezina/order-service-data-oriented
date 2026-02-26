package com.bezina.order_service2.model.order;

import com.bezina.order_service2.model.product.Product;

public record OrderItem(Product product,
                        int quantity) {
    public double subTotal() {
        return product.unitPrice() * quantity;
    }

    public double discountedTotal() {
        return product.discountedPrice() * quantity;
    }
}
