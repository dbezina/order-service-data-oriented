package com.bezina.order_service2.service.impl;

import com.bezina.order_service2.client.BillingClient;
import com.bezina.order_service2.client.PaymentClient;
import com.bezina.order_service2.exception.ApplicationExceptions;
import com.bezina.order_service2.model.PriceSummary;
import com.bezina.order_service2.model.customer.Customer;
import com.bezina.order_service2.model.invoice.Invoice;
import com.bezina.order_service2.model.invoice.InvoiceRequest;
import com.bezina.order_service2.model.order.Order;
import com.bezina.order_service2.model.payment.PaymentRequest;
import com.bezina.order_service2.model.payment.PaymentStatus;
import com.bezina.order_service2.service.PaymentBillingService;

public class PaymentBillingServiceImpl implements PaymentBillingService {
    private final PaymentClient paymentClient;
    private final BillingClient billingClient;

    public PaymentBillingServiceImpl(PaymentClient paymentClient, BillingClient billingClient) {
        this.paymentClient = paymentClient;
        this.billingClient = billingClient;
    }

    @Override
    public Invoice processPayment(Order order, PriceSummary priceSummary) {
        var paymentRequest = new PaymentRequest(order.customer().id(),order.orderId(),priceSummary.finalAmount());
        var paymentStatus = this.paymentClient.process(paymentRequest);
        return switch (paymentStatus){
            case PaymentStatus.Processed processed -> this.toPaidInvoice(order, priceSummary, processed);
            case PaymentStatus.Declined declined-> this.toUnpaidInvoice(order, priceSummary, declined);
        };
    }

    private Invoice toPaidInvoice(Order order, PriceSummary priceSummary, PaymentStatus.Processed processed) {
        var request = new InvoiceRequest.Paid(order.orderId(), order.customer().id(), processed.transactionId(), priceSummary);
        return this.billingClient.createInvoice(request);
    }

    private Invoice toUnpaidInvoice(Order order, PriceSummary priceSummary, PaymentStatus.Declined declined ) {
        return switch (order.customer()){
            case Customer.Regular _ -> ApplicationExceptions.declinedPayment(declined);
            case Customer.Business business -> toUnpaidInvoice(order, priceSummary, business);
        };
    }

    private Invoice toUnpaidInvoice(Order order, PriceSummary priceSummary, Customer.Business business ) {
        var request = new InvoiceRequest.Unpaid(
                order.orderId(),
                order.customer().id(),
                business.name(),
                business.taxId(),
                priceSummary
        );
        return this.billingClient.createInvoice(request);
    }


}
