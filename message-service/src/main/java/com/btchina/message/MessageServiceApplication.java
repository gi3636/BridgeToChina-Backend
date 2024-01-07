package com.btchina.message;

import com.btchina.amqp.config.EnableMq;
import com.btchina.common.mybatis.config.EnableMybatisPlus;
import com.btchina.common.swagger.config.EnableSwagger;
import com.btchina.core.config.EnableLongToStringConfig;
import com.btchina.core.exception.EnableDefaultExceptionAdvice;
import com.btchina.message.netty.annotation.EnableNettyServer;
import com.btchina.redis.config.EnableRedisSerialize;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableNettyServer
@EnableMybatisPlus
@EnableSwagger
@EnableRedisSerialize
@SpringBootApplication
@EnableDefaultExceptionAdvice
@EnableLongToStringConfig
@EnableMq
@EnableFeignClients(basePackages = {"com.btchina.**.**.feign"})
public class MessageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageServiceApplication.class, args);
    }

}
