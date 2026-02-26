package com.bezina.order_service2.controller.advice;


import com.bezina.order_service2.exception.ApplicationError;
import com.bezina.order_service2.exception.ApplicationException;
import com.bezina.order_service2.exception.DomainError;
import com.bezina.order_service2.exception.SystemError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.function.Consumer;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class )
    public ProblemDetail handleApplicationException( ApplicationException e ) {
        return switch (e.getError()){
            case DomainError.EntityNotFound error -> this.toProblemDetail(error);
            case DomainError.PaymentIsDeclined error -> this.toProblemDetail(error);
            case DomainError.ProductDiscontinued error -> this.toProblemDetail(error);
            case SystemError.RemoteServiceError error-> this.toProblemDetail(error);
        };
    }

    private ProblemDetail toProblemDetail(DomainError.EntityNotFound error) {
        return this.build(HttpStatus.BAD_REQUEST, error, (ProblemDetail problemDetail)  -> {
            problemDetail.setTitle("Not Found");
            problemDetail.setDetail("Unable to find the requested entity %s for the given id %s"
                    .formatted(error.entity(), error.Id()));
        });
    }

    private ProblemDetail toProblemDetail(DomainError.PaymentIsDeclined error) {
        return this.build(HttpStatus.PAYMENT_REQUIRED, error, (ProblemDetail problemDetail)  -> {
            problemDetail.setTitle("Payment Required");
            problemDetail.setDetail("Payment for the order %s was declined. Please, try again"
                    .formatted(error.orderId()));
        });
    }
    private ProblemDetail toProblemDetail(DomainError.ProductDiscontinued error) {
        return this.build(HttpStatus.BAD_REQUEST, error, (ProblemDetail problemDetail)  -> {
            problemDetail.setTitle("Product discontinued");
            problemDetail.setDetail("The product (id= %s ) is discontinued. Checkout our alternatives."
                    .formatted(error.productId()));
        });
    }
    private ProblemDetail toProblemDetail(SystemError.RemoteServiceError error) {
        return this.build(HttpStatus.SERVICE_UNAVAILABLE, error, (ProblemDetail problemDetail)  -> {
            problemDetail.setTitle("Service unavailable");
            problemDetail.setDetail("Unable to fulfill the order. Please try gain later. ");

        });
    }

    private ProblemDetail build (HttpStatus status, ApplicationError error, Consumer<ProblemDetail> consumer) {
        var problem = ProblemDetail.forStatus(status);
        problem.setProperty("additionalInformation", error);
        consumer.accept(problem);
        return problem;
    }
}
