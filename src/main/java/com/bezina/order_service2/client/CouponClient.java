package com.bezina.order_service2.client;

import com.bezina.order_service2.model.coupon.Coupon;

public interface CouponClient {
    Coupon getCoupon(String couponCode);

}
