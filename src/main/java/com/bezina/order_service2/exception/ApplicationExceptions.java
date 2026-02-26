package com.bezina.order_service2.exception;

import com.bezina.order_service2.model.payment.PaymentStatus;
import com.bezina.order_service2.model.product.Product;
import com.bezina.order_service2.model.product.ProductStatus;

public class ApplicationExceptions {

    public static <T> T customerNotFound( String id) {
        var error = new DomainError.EntityNotFound(DomainError.Entity.CUSTOMER,id);
        throw new ApplicationException(error);
    }
    public static <T> T productNotFound( String id) {
        var error = new DomainError.EntityNotFound(DomainError.Entity.PRODUCT,id);
        throw new ApplicationException(error);
    }

    public static <T> T discontinuedProduct(ProductStatus.Discontinued discontinued) {
        var error = new DomainError.ProductDiscontinued(
                discontinued.productId(),
                discontinued.recommendedProducts()
        );
        throw new ApplicationException(error);
    }

    public static <T> T declinedPayment(PaymentStatus.Declined  declinedPayment) {
        var error = new DomainError.PaymentIsDeclined(
                declinedPayment.orderId(),
                declinedPayment.amount()
        );
        throw new ApplicationException(error);
    }

    public static <T> T remoteServiceError(String service, String message) {
        var error = new SystemError.RemoteServiceError(
                service, message
        );
        throw new ApplicationException(error);
    }

}
