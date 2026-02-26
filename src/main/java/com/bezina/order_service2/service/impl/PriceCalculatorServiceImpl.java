package com.bezina.order_service2.service.impl;

import com.bezina.order_service2.model.PriceSummary;
import com.bezina.order_service2.model.coupon.Coupon;
import com.bezina.order_service2.model.order.Order;
import com.bezina.order_service2.model.order.OrderItem;
import com.bezina.order_service2.orchestrator.OrderState;
import com.bezina.order_service2.service.PriceCalculatorService;

public class PriceCalculatorServiceImpl implements PriceCalculatorService {
    private static final double MINIMUM_PRICE = 5d;

@Override
public PriceSummary calculate(Order order) {


    var state = order.customer().address().state();

    var subTotal = order.orderItems().stream()
            .mapToDouble(OrderItem::subTotal)
            .sum();

    var coupon = order.coupon();
    System.out.println("coupon "+coupon.toString());
    var discountedTotal = order.orderItems().stream()
            .mapToDouble((OrderItem item) -> this.applyCoupon(coupon, item.discountedTotal()))
            .sum();

    var tax = discountedTotal * getTaxRate(state);

    return new PriceSummary(
            subTotal,
            subTotal - discountedTotal,
            tax,
            discountedTotal + tax
    );
}
     private double applyCoupon(Coupon coupon, double amount) {
        System.out.printf("Applying coupon: %f\n, %s", amount, coupon.toString());
        var payableAmount = switch (coupon){
            case Coupon.Flat flat -> amount-flat.discount();
            case Coupon.Percentage percentage-> applyPercentageCoupon(percentage, amount);
            case Coupon.None _ -> amount;
        };
        System.out.println("payableAmount "+payableAmount);
        return Math.max(payableAmount,MINIMUM_PRICE);
     }

    private double applyPercentageCoupon(Coupon.Percentage percentage, double amount) {
        double discounted = amount * percentage.percent() / 100d;
        double resultDiscounted = Math.min(discounted, percentage.maxDiscount());
        return amount - resultDiscounted;
    }


    private double getTaxRate(String state) {
        return switch (state) {
            case "MI" -> 0.06;
            case "WA" -> 0.09;
            case "FL" -> 0.07;
            case "TX", "NY", "CA", "IL", "AZ" -> 0.08;
            default -> 0.05; // Default tax rate
        };
    }
}
