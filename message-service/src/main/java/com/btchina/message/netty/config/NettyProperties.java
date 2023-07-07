package com.btchina.message.netty.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "netty")
@Data
@Configuration
public class NettyProperties {

    // 连接超时时间 默认为30s
    private Integer timeout = 30000;

    // 服务器主端口 默认9000
    private Integer port = 9500;

    // 服务器备用端口
    private Integer portSalve = 9501;

}
