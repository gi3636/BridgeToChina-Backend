package com.btchina.gateway;

import com.btchina.common.swagger.config.EnableSwagger;
import com.btchina.core.config.EnableJwt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
;
@SpringBootApplication
@EnableSwagger
@EnableJwt
public class GatewayApplication
{
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
