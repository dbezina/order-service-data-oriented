package com.bezina.order_service2.model.shipping;

import java.util.List;
import java.util.UUID;

public record ShippingResponse(UUID orderId,
                               List<Shipment> shipments) {
}
