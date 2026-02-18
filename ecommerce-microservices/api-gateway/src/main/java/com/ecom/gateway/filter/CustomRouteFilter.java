package com.ecom.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomRouteFilter extends AbstractGatewayFilterFactory<CustomRouteFilter.Config> {

    public CustomRouteFilter() {
        super(Config.class);
    }

    public static class Config {
        // filter configuration properties (optional)
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            System.out.println("Route filter : Before routing");
            exchange.getRequest().mutate().header("X-Route-Header", "Added-Bu-GateWay").build();

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("Route filter : After routing");
            }));
        };
    }
}
