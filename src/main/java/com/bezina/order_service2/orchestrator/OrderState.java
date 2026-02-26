package com.bezina.order_service2.orchestrator;

import com.bezina.order_service2.model.*;
import com.bezina.order_service2.model.invoice.Invoice;
import com.bezina.order_service2.model.order.CreateOrderCommand;
import com.bezina.order_service2.model.order.Order;
import com.bezina.order_service2.model.shipping.Shipment;

import java.util.List;

public sealed interface OrderState {
    record Placed(CreateOrderCommand request) implements OrderState {
    }

    record Validated(Order order) implements OrderState {
    }

    record Priced(Order order
            , PriceSummary priceSummary) implements OrderState {
    }

    record Invoiced(Order order
            , Invoice invoice) implements OrderState {
    }

    record Shipped(Order order,
                   Invoice invoice,
                   List<Shipment> shipments) implements OrderState {
    }

    record Fulfilled(Order order,
                     Invoice invoice,
                     List<Shipment> shipments) implements OrderState {
    }


}
