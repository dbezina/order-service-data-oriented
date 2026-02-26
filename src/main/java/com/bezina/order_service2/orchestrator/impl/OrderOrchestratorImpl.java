package com.bezina.order_service2.orchestrator.impl;

import com.bezina.order_service2.orchestrator.OrderOrchestrator;
import com.bezina.order_service2.orchestrator.OrderState;
import com.bezina.order_service2.service.PaymentBillingService;
import com.bezina.order_service2.service.PriceCalculatorService;
import com.bezina.order_service2.service.RequestValidatorService;
import com.bezina.order_service2.service.ShippingService;

public class OrderOrchestratorImpl implements OrderOrchestrator {
    private final PaymentBillingService paymentService;
    private final PriceCalculatorService priceCalculatorService;
    private final RequestValidatorService validatorService;
    private final ShippingService shippingService;

    public OrderOrchestratorImpl(RequestValidatorService validatorService,
                                 PriceCalculatorService priceCalculatorService,
                                 PaymentBillingService paymentService,
                                 ShippingService shippingService) {
        this.paymentService = paymentService;
        this.priceCalculatorService = priceCalculatorService;
        this.validatorService = validatorService;
        this.shippingService = shippingService;
    }


    @Override
    public OrderState handle(OrderState.Placed placed) {
        var order = this.validatorService.validate(placed.request());
        return new OrderState.Validated(order);
    }

    @Override
    public OrderState handle(OrderState.Validated validated) {
        var priceSummary = this.priceCalculatorService.calculate(validated.order());
        return new OrderState.Priced(validated.order(), priceSummary);
    }

    @Override
    public OrderState handle(OrderState.Priced priced) {
        var invoice = this.paymentService.processPayment(priced.order(), priced.priceSummary());
        return new OrderState.Invoiced(priced.order(), invoice);
    }

    @Override
    public OrderState handle(OrderState.Invoiced invoiced) {
        var shipments = this.shippingService.scheduleShipping(invoiced.order()).shipments();
        return new OrderState.Shipped(invoiced.order(), invoiced.invoice(), shipments);
    }

    // this step might look unnecessary. but I have a dedicated lecture to explain why it is required.
    @Override
    public OrderState handle(OrderState.Shipped shipped) {
        return new OrderState.Fulfilled(shipped.order(), shipped.invoice(), shipped.shipments());
    }

}
