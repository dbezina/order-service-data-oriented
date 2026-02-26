package com.bezina.order_service2.model.invoice;

import com.bezina.order_service2.model.PriceSummary;

import java.util.UUID;

public sealed interface InvoiceRequest {
    record Paid(UUID orderId,
                String customerId,
                String transactionId,
                PriceSummary priceSummary) implements InvoiceRequest {

    }

    record Unpaid(UUID orderId,
                  String customerId,
                  String businessName,
                  String businessTaxId,
                  PriceSummary priceSummary) implements InvoiceRequest {

    }

}
