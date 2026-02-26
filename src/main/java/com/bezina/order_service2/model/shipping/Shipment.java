package com.bezina.order_service2.model.shipping;

public record Shipment(String shipmentId,
                       String productId,
                       int quantity,
                       String shippingAddress,
                       TrackingDetails trackingDetails) {
}
