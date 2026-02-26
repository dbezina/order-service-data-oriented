package com.bezina.order_service2.model.customer;

import com.bezina.order_service2.model.Address;

public sealed interface Customer {
    String id();
    String name();
    Address address();

    record Regular(String id,
                   String name,
                   Address address) implements Customer {

    }

    record Business(String id,
                    String name,
                    String taxId,
                    Address address) implements Customer {

    }
}
