package com.btchina.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class IgnoreGlobalFilter extends AbstractGatewayFilterFactory<IgnoreGlobalFilter.Config> implements Ordered {

    public IgnoreGlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return this::filter;
    }

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("经过了IgnoreGlobalFilter");
        exchange.getAttributes().put(AuthorizeFilter.ATTRIBUTE_IGNORE_GLOBAL_FILTER, true);
        return chain.filter(exchange);
    }

    public static class Config {

    }

    @Override
    public int getOrder() {
        return -101;
    }

    @Override
    public String name() {
        return "IgnoreGlobalFilter";
    }
}
