package com.bezina.order_service2.client.impl;

import com.bezina.order_service2.exception.ApplicationException;
import com.bezina.order_service2.exception.ApplicationExceptions;
import com.bezina.order_service2.model.product.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

abstract class AbstractServiceClient {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractServiceClient.class);

    protected abstract String getServiceName();

    protected <T> T executeRequest(Supplier<T> supplier, Map<Integer, Supplier<T>> errorMap) {
        try {
            var t = supplier.get();
            LOG.info("response: {}", t);
            return t;
        } catch (HttpStatusCodeException ex) {
            LOG.error("Error response from: {}", this.getServiceName(), ex);
            return Optional.ofNullable(errorMap.get(ex.getStatusCode().value()))
                    .map(Supplier::get)
                    .orElseGet(()-> ApplicationExceptions
                            .remoteServiceError(
                                    this.getServiceName(),
                                    ex.getMessage()));
        }
    }
}
