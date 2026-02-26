package com.bezina.order_service2.util;

import com.bezina.order_service2.model.invoice.Invoice;
import com.bezina.order_service2.model.order.CreateOrderCommand;
import com.bezina.order_service2.model.order.Order;
import com.bezina.order_service2.model.order.OrderRequest;
import com.bezina.order_service2.model.order.OrderResponse;
import com.bezina.order_service2.model.product.Product;
import com.bezina.order_service2.model.shipping.Shipment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


public class DomainDtoMapper {
    private static final String COMPLETED = "COMPLETED";
    private static final String CANCELLED = "CANCELLED";
    private static final String PAID = "PAID";
    private static final String UNPAID = "UNPAID";

    public static CreateOrderCommand toCreateOrderCommand(OrderRequest orderRequest) {
        return CreateOrderCommand.create(
                orderRequest.customerId(),
                orderRequest.productId(),
                orderRequest.quantity(),
                orderRequest.couponCode()
        );
    }
    public static OrderResponse toOrderResponse(Order order, Invoice invoice, List<Shipment> shipments) {
        return new OrderResponse(
                order.orderId(),
                COMPLETED,
                toProducts(order),
                toInvoiceDetails(invoice),
                shipments

        );
    }

    private static OrderResponse.InvoiceDetails toInvoiceDetails(Invoice invoice) {

        return switch (invoice) {

            case Invoice.Paid paid -> new OrderResponse.InvoiceDetails(
                    paid.id(),
                    PAID,
                    paid.priceSummary(),
                    Optional.empty() // уже оплачено
            );

            case Invoice.Unpaid unpaid -> new OrderResponse.InvoiceDetails(
                    unpaid.id(),
                    UNPAID,
                    unpaid.priceSummary(),
                    Optional.of(unpaid.paymentDue())
            );
        };
    }

    private static List<OrderResponse.Product> toProducts(Order order) {
        return order.orderItems()
                .stream()
                .flatMap(orderItem -> {

                    var quantity = orderItem.quantity();
                    var product = orderItem.product();
                    return switch (product) {
                        case Product.Single single ->
                                Stream.of(toProduct(single, quantity));
                        case Product.Bundle bundle ->
                                bundle.items()
                                        .stream()
                                        .map(single -> toProduct(single, quantity));
                    };
                })
                .toList();
    }
    private static OrderResponse.Product toProduct(Product.Single single, int quantity){
        return new OrderResponse.Product(
                single.productId(),
                single.name(),
                single.price(),
                quantity
        );
    }

}
