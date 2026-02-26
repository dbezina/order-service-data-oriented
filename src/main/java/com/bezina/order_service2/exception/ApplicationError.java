package com.bezina.order_service2.exception;

public sealed interface ApplicationError  permits SystemError, DomainError{
}
