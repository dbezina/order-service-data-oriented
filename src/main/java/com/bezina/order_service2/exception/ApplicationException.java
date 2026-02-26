package com.bezina.order_service2.exception;

public class ApplicationException extends RuntimeException {
  private final ApplicationError error;

  public ApplicationException(ApplicationError applicationError) {
      this.error = applicationError;
  }

    public ApplicationError getError() {
        return error;
    }
}
