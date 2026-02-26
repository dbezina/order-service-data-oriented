package com.bezina.order_service2.client.impl;

import com.bezina.order_service2.client.CouponClient;
import com.bezina.order_service2.exception.ApplicationExceptions;
import com.bezina.order_service2.model.coupon.Coupon;
import com.bezina.order_service2.model.customer.Customer;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.function.Supplier;

public class CouponServiceClient extends AbstractServiceClient implements CouponClient {
    private final RestClient restClient ;

    public CouponServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Coupon getCoupon(String code) {
        System.out.printf("getCoupon: couponCode=%s\n", code);
        var errorMap = Map.<Integer, Supplier<Coupon>>of(
                404, Coupon::none);
        //product.service.url
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Coupon result = this.executeRequest(
                () -> this.restClient.get()
                        .uri("/{code}", code)
                        .retrieve()
                        .body(Coupon.class),
                errorMap
        );
        System.out.println("getCoupon: executeRequest " + result);
        return result;
    }

    @Override
    protected String getServiceName() {
        return "coupon-service" ;
    }
}
