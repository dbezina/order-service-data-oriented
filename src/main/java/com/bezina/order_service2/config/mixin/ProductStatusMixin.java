package com.bezina.order_service2.config.mixin;

import com.bezina.order_service2.model.product.ProductStatus;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.boot.jackson.JacksonMixin;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION,
        defaultImpl = ProductStatus.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(ProductStatus.Active.class),
        @JsonSubTypes.Type(ProductStatus.Discontinued.class),
})
@JacksonMixin(ProductStatus.class)
public class ProductStatusMixin {
}
