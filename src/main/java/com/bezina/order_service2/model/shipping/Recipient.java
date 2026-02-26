package com.bezina.order_service2.model.shipping;

import com.bezina.order_service2.model.Address;

public record Recipient(String name,
                        Address address) {
}
