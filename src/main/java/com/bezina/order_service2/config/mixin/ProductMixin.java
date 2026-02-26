package com.bezina.order_service2.config.mixin;

import com.bezina.order_service2.model.product.Product;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.boot.jackson.JacksonMixin;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION,
        defaultImpl = Product.Single.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(Product.Single.class),
        @JsonSubTypes.Type(Product.Bundle.class),
})
@JacksonMixin(Product.class)
public class ProductMixin {
}
