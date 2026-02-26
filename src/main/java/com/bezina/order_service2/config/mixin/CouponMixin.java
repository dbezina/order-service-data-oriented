package com.bezina.order_service2.config.mixin;

import com.bezina.order_service2.model.coupon.Coupon;
import com.bezina.order_service2.model.customer.Customer;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.boot.jackson.JacksonMixin;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION,
        defaultImpl = Coupon.None.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(Coupon.None.class),
        @JsonSubTypes.Type(Coupon.Flat.class),
        @JsonSubTypes.Type(Coupon.Percentage.class)
})
@JacksonMixin(Coupon.class)
public class CouponMixin {
}