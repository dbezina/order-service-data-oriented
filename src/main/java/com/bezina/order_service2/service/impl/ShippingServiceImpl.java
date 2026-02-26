package com.bezina.order_service2.service.impl;

import com.bezina.order_service2.client.ShippingClient;
import com.bezina.order_service2.model.order.Order;
import com.bezina.order_service2.model.product.Product;
import com.bezina.order_service2.model.shipping.Recipient;
import com.bezina.order_service2.model.shipping.ShipmentItem;
import com.bezina.order_service2.model.shipping.ShippingRequest;
import com.bezina.order_service2.model.shipping.ShippingResponse;
import com.bezina.order_service2.service.ShippingService;

import java.util.List;
import java.util.stream.Stream;


public class ShippingServiceImpl implements ShippingService {
    private final ShippingClient shippingClient;

    public ShippingServiceImpl(ShippingClient shippingClient) {
        this.shippingClient = shippingClient;
    }

    @Override
    public ShippingResponse scheduleShipping(Order order) {
        return shippingClient.scheduleShipping(getShippingRequest(order));
    }

    private ShippingRequest getShippingRequest(Order order) {
        var recipient = new Recipient(order.customer().name(), order.customer().address());
        var shippingItems = order.orderItems()
                .stream()
                .flatMap(orderItem -> {
                    var quantity = orderItem.quantity();
                    var product = orderItem.product();

                    return switch (product) {

                        case Product.Single single ->
                                Stream.of(new ShipmentItem(
                                        single.productId(),
                                        quantity
                                ));

                        case Product.Bundle bundle ->
                                bundle.items()
                                        .stream()
                                        .map(Product.Single::productId)
                                        .map(id -> new ShipmentItem(id, quantity));
                    };

                }).toList();
        return new ShippingRequest(order.orderId(), recipient, shippingItems);
    }

}
