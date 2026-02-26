package com.bezina.order_service2.service;

import com.bezina.order_service2.model.PriceSummary;
import com.bezina.order_service2.model.invoice.Invoice;
import com.bezina.order_service2.model.order.Order;

public interface PaymentBillingService {
    Invoice processPayment(Order order, PriceSummary priceSummary);
}
