package com.bezina.order_service2.model.product;

import java.util.List;

public sealed interface Product {
    String productId();
    String name();
    double unitPrice();
    double discountedPrice();

    record Single(String productId,
                  String name,
                  double price) implements Product {
        @Override
        public double unitPrice() {
            return price;
        }

        @Override
        public double discountedPrice() {
            return price; // нет скидки
        }

    }

    record Bundle(String productId,
                  String name,
                  double originalPrice,
                  double discountedPrice,
                  List<Single> items) implements Product {
        @Override
        public double unitPrice() {
            return originalPrice;
        }

        @Override
        public double discountedPrice() {
            return discountedPrice;
        }
    }
}
