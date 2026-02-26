package com.bezina.order_service2.config;

import com.bezina.order_service2.client.*;
import com.bezina.order_service2.client.impl.*;
import com.bezina.order_service2.orchestrator.OrderOrchestrator;
import com.bezina.order_service2.orchestrator.impl.OrderOrchestratorImpl;
import com.bezina.order_service2.service.*;
import com.bezina.order_service2.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ApplicationConfiguration {
    private static Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);
    private final RestClient.Builder builder;

    public ApplicationConfiguration (RestClient.Builder builder) {
        this.builder = builder.requestInterceptor(new LoggingInterceptor());
    }
    private RestClient buildRestClient (String baseUrl) {
        logger.info("baseUrl : " + baseUrl);
        return this.builder.baseUrl(baseUrl).build();
    }
    ///////////////CLIENTS
    @Bean
    public ProductClient productClient (@Value("${product.service.url}")String baseUrl) {
        return new ProductServiceClient(buildRestClient(baseUrl));
    }
    @Bean
    public CustomerClient customerClient (@Value("${customer.service.url}")String baseUrl) {
        return new CustomerServiceClient(buildRestClient(baseUrl));
    }
    @Bean
    public PaymentClient paymentClient (@Value("${payment.service.url}")String baseUrl) {
        return new PaymentServiceClient(buildRestClient(baseUrl));
    }
    @Bean
    public ShippingClient shippingClient (@Value("${shipping.service.url}")String baseUrl) {
        return new ShippingServiceClient(buildRestClient(baseUrl));
    }
    @Bean
    public BillingClient billingClient (@Value("${billing.service.url}")String baseUrl) {
        return new BillingServiceClient(buildRestClient(baseUrl));
    }
    @Bean
    public CouponClient couponClient (@Value("${coupon.service.url}")String baseUrl) {
        return new CouponServiceClient(buildRestClient(baseUrl));
    }
    ///////////////SERVICES
    @Bean
    public RequestValidatorService requestValidatorService (ProductClient productClient,
                                                            CustomerClient customerClient,
                                                            CouponClient couponClient) {
        return new RequestValidatorServiceImpl(productClient, customerClient, couponClient);
    }
    @Bean
    public PriceCalculatorService priceCalculatorService () {
        return new PriceCalculatorServiceImpl();
    }
    @Bean
    public PaymentBillingService paymentBillingService (PaymentClient paymentClient, BillingClient billingClient) {
        return new PaymentBillingServiceImpl(paymentClient, billingClient);
    }

    @Bean
    public ShippingService shippingService (ShippingClient shippingClient) {
        return new ShippingServiceImpl(shippingClient);
    }

    @Bean
    public OrderOrchestrator orderOrchestrator(RequestValidatorService validatorService,
                                               PriceCalculatorService priceCalculator,
                                               PaymentBillingService paymentBillingService,
                                               ShippingService shippingService) {
        return new OrderOrchestratorImpl(
                validatorService,
                priceCalculator,
                paymentBillingService,
                shippingService
        );
    }

    @Bean
    public OrderService orderService(OrderOrchestrator orderOrchestrator){
        return new OrderServiceImpl(orderOrchestrator);
    }
}
