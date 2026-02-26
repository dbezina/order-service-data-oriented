package com.bezina.order_service2.client;

import com.bezina.order_service2.model.invoice.Invoice;
import com.bezina.order_service2.model.invoice.InvoiceRequest;

public interface BillingClient {
    Invoice createInvoice(InvoiceRequest invoiceRequest);

}
