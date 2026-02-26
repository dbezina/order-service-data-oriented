package com.bezina.order_service2.model.shipping;

import java.time.LocalDate;

public record TrackingDetails(String carrier,
                              String trackingNumber,
                              LocalDate estimatedDeliveryDate) {
}
