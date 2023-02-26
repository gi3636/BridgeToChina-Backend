package com.btchina.answer;

import com.btchina.amqp.config.EnableMq;
import com.btchina.common.mybatis.config.EnableMybatisPlus;
import com.btchina.common.swagger.config.EnableSwagger;
import com.btchina.core.config.EnableLongToStringConfig;
import com.btchina.core.exception.EnableDefaultExceptionAdvice;
import com.btchina.feign.config.DefaultFeignConfiguration;
import com.btchina.redis.config.EnableRedisSerialize;
import org.checkerframework.checker.units.qual.A;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableMybatisPlus
@EnableSwagger
@EnableRedisSerialize
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.btchina.feign.clients"}, defaultConfiguration = DefaultFeignConfiguration.class)
@EnableDefaultExceptionAdvice
@EnableLongToStringConfig
@EnableMq
public class AnswerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnswerServiceApplication.class, args);
    }

}
