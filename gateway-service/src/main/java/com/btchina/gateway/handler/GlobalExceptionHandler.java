package com.btchina.gateway.handler;


import com.btchina.core.api.CommonResult;
import com.btchina.core.util.MonoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * 网关异常通用处理器，只作用在webflux 环境下 , 优先级低于 {@link ResponseStatusExceptionHandler} 执行
 *
 * @author 阿杆
 */
@Slf4j
@Order(-1)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    //@Resource
    //private VisitRecordService visitRecordService;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        // 设置返回信息和HTTP状态码
        CommonResult<Object> result;
        HttpStatus httpStatus;
        if (ex instanceof ResponseStatusException) {
            // HTTP状态码异常
            log.warn("Http Status Warn : {}, url: {}", ex.getMessage(), request.getURI());
            httpStatus = ((ResponseStatusException) ex).getStatus();
        } else {
            // 系统异常
            log.error("Error Gateway, {}: {}, url: {}", ex.getClass(), ex.getMessage(), request.getURI());
            httpStatus = HttpStatus.BAD_GATEWAY;
        }
        System.out.println("httpStatus:" + httpStatus);
        result = CommonResult.failed(500, ex.getMessage(), false);
        response.setStatusCode(httpStatus);

        // 由于系统异常，不会正常走过滤器，故手动保存访问记录
        //visitRecordService.add(exchange);

        return MonoUtils.buildMonoWrap(response, result);
    }

}
