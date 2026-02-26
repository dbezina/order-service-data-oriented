package com.bezina.order_service2.model;

// finalAmount = subTotal - discountApplied + taxAmount
public record PriceSummary(double subTotal,
                           double discountApplied,
                           double taxAmount,
                           double finalAmount) {
}
