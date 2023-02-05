package com.btchina.gateway.config;

import com.btchina.gateway.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 网关配置
 *
 * @author 阿杆
 */
@Configuration(proxyBeanMethods = false)
public class GatewayConfig {

	@Bean
	public GlobalExceptionHandler globalExceptionHandler() {
		return new GlobalExceptionHandler();
	}

}
