package com.bezina.order_service2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {
    private static Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpRequestInterceptor andThen(ClientHttpRequestInterceptor interceptor) {
        return ClientHttpRequestInterceptor.super.andThen(interceptor);
    }

    @Override
    public ClientHttpRequestExecution apply(ClientHttpRequestExecution execution) {
        return ClientHttpRequestInterceptor.super.apply(execution);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logger.info("__________________________________________");
        logger.info(request.getMethod() + " " + request.getURI());
        logger.info("request body : " + new String(body));
        return execution.execute(request, body);
    }
}
