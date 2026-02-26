package com.bezina.order_service2.model.order;

import com.bezina.order_service2.model.PriceSummary;
import com.bezina.order_service2.model.shipping.Shipment;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record OrderResponse(UUID orderId,
                            String status,
                            List<Product> products,
                            InvoiceDetails invoice,
                            List<Shipment> shipments) {

    public record Product(String id,
                          String name,
                          double unitPrice,
                          int quantity) {

    }
    @JsonInclude(JsonInclude.Include.NON_ABSENT) // not to see null
    public record InvoiceDetails(String invoiceId,
                                 String paymentStatus,
                                 PriceSummary price,
                                 Optional<LocalDate> paymentDate) {

    }
}
