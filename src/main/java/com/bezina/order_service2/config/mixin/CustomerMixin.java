package com.bezina.order_service2.config.mixin;

import com.bezina.order_service2.model.customer.Customer;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.boot.jackson.JacksonMixin;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION,
        defaultImpl = Customer.Regular.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(Customer.Regular.class),
        @JsonSubTypes.Type(Customer.Business.class),
})
@JacksonMixin(Customer.class)
public class CustomerMixin {
}
