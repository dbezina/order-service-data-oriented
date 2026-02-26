package com.bezina.order_service2.config.mixin;

import com.bezina.order_service2.model.invoice.Invoice;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.boot.jackson.JacksonMixin;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION,
        defaultImpl = Invoice.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(Invoice.Paid.class),
        @JsonSubTypes.Type(Invoice.Unpaid.class),
})
@JacksonMixin(Invoice.class)
public class InvoiceMixin {
}
