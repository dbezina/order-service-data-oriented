package com.bezina.order_service2.client.impl;

import com.bezina.order_service2.client.BillingClient;
import com.bezina.order_service2.model.invoice.Invoice;
import com.bezina.order_service2.model.invoice.InvoiceRequest;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class BillingServiceClient extends AbstractServiceClient implements BillingClient {
    private final RestClient restClient;

    public BillingServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }
    @Override
    protected String getServiceName() {
        return "billing-service";
    }

    @Override
    public Invoice createInvoice(InvoiceRequest invoiceRequest) {

        return switch (invoiceRequest){
            case InvoiceRequest.Paid _ -> this.executeRequest("/invoices/paid",invoiceRequest);
            case InvoiceRequest.Unpaid _ -> this.executeRequest("/invoices/unpaid",invoiceRequest);
        };
    }

    private Invoice executeRequest(String path, InvoiceRequest  invoiceRequest ) {
        return this.executeRequest(
                ()-> this.restClient.post()
                        .uri(path)
                        .body(invoiceRequest)
                        .retrieve()
                        .body(Invoice.class),
                Collections.emptyMap()
        );
    }


}
