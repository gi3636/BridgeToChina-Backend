package com.btchina.gateway.filter;

import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Component
@Slf4j
public class IgnoreGlobalFilter extends AbstractGatewayFilterFactory<IgnoreGlobalFilter.Config> implements Ordered {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
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
        // 通过request中的id来判断用户
        ServerHttpRequest request = exchange.getRequest();
        // 获取token
        String token = request.getHeaders().getFirst("token");
        if (token == null) {
            return chain.filter(exchange);
        }
        System.out.println("token = " + token);
        try {
            Long id = jwtTokenUtil.getIdFromToken(token);
            String username = jwtTokenUtil.getUserNameFromToken(token);
            // 将用户信息存储在request header中
            Consumer<HttpHeaders> httpHeaders = httpHeader -> {
                httpHeader.set("user-id", id.toString());
                httpHeader.set("username", username);
            };
            ServerHttpRequest build = request.mutate().headers(httpHeaders).build();
            exchange = exchange.mutate().request(build).build();
        } catch (Exception e) {
            throw GlobalException.from(ResultCode.TOKEN_INVALID);
        }
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
