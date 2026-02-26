package com.bezina.order_service2.service.impl;

import com.bezina.order_service2.model.order.OrderRequest;
import com.bezina.order_service2.model.order.OrderResponse;
import com.bezina.order_service2.orchestrator.OrderOrchestrator;
import com.bezina.order_service2.orchestrator.OrderState;
import com.bezina.order_service2.service.OrderService;
import com.bezina.order_service2.util.DomainDtoMapper;

public class OrderServiceImpl implements OrderService {
    private final OrderOrchestrator orderOrchestrator;

    public OrderServiceImpl(OrderOrchestrator orderOrchestrator) {
        this.orderOrchestrator = orderOrchestrator;
    }

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        var command = DomainDtoMapper.toCreateOrderCommand(request);
        var placedOrderState = new OrderState.Placed(command);
        var orderState = this.orderOrchestrator.orchestrate(placedOrderState);
        return switch (orderState){
            case OrderState.Fulfilled fulfilled ->
                    DomainDtoMapper.toOrderResponse(
                            fulfilled.order(),
                            fulfilled.invoice(),
                            fulfilled.shipments());
            default -> throw new IllegalStateException("Unexpected value: " + orderState);//should never happen
        }
              ;
    }
}
