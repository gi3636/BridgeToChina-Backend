package com.btchina.gateway.filter;

import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.gateway.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 权限认证过滤器
 */
@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter {

	public final static String ATTRIBUTE_IGNORE_GLOBAL_FILTER = "@IgnoreGlobalFilter";
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		System.out.println("exchange: " + exchange.getRequest().getURI().getPath());
		// 不判断token的过滤器
		if (exchange.getAttribute(ATTRIBUTE_IGNORE_GLOBAL_FILTER) != null
				&& exchange.getAttribute(ATTRIBUTE_IGNORE_GLOBAL_FILTER).equals(true)) {
			return chain.filter(exchange);
		}
		// 通过request中的id来判断用户
		ServerHttpRequest request = exchange.getRequest();
		// 获取token
		String token = request.getHeaders().getFirst("token");
		log.info("token: {}", token);
		////验证token
		if (StringUtils.isBlank(token)) {
			throw GlobalException.from(ResultCode.UNAUTHORIZED);
		}
		if (!jwtTokenUtil.validateToken(token)) {
			throw GlobalException.from(ResultCode.UNAUTHORIZED);
		}

		//if (StringUtils.hasText(token)) {
		//	// 验证、解析token
		//
		//}
		// 放行
		return chain.filter(exchange);
	}


	/**
	 * 获取存储在request header上的用户信息，但有可能为空
	 *
	 * @param request 请求体
	 * @return 用户id
	 */
	public static Long getUserId(ServerHttpRequest request) {
		Long res = null;
		String identity = request.getHeaders().getFirst("token");
		if (identity != null) {
			try {
				res = Long.parseLong(identity);
			} catch (NumberFormatException ignored) {
			}
		}
		return res;
	}

}
