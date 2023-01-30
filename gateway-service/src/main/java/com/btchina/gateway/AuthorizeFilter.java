package com.btchina.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(-1)
@Component
public class AuthorizeFilter implements GlobalFilter , Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.请求参数
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String,String> params = request.getQueryParams();
        //2.获取请求参数中的authorization
        String auth = params.getFirst("authorization");

        //3.判断参数值是否等于admin
        if ("admin".equals(auth)){
            //4.是，放行
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
        //5.否，拦截
        //5.1 设置状态码
        // exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        // return exchange.getResponse().setComplete();

    }

    @Override
    public int getOrder() {
        return -1 ;
    }
}
