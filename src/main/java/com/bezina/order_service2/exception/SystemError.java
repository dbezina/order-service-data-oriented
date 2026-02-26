package com.bezina.order_service2.exception;

public sealed interface SystemError extends ApplicationError{
    record RemoteServiceError(String service,
                              String message) implements SystemError {

    }
}
