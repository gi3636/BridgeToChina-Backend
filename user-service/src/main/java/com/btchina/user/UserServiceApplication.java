package com.btchina.user;

import com.btchina.amqp.config.EnableMq;
import com.btchina.common.mybatis.config.EnableMybatisPlus;
import com.btchina.common.swagger.config.EnableSwagger;
import com.btchina.core.config.EnableJwt;
import com.btchina.core.config.EnableLongToStringConfig;
import com.btchina.core.exception.EnableDefaultExceptionAdvice;
import com.btchina.feign.config.DefaultFeignConfiguration;
import com.btchina.redis.config.EnableRedisSerialize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.ConfigurableEnvironment;


@EnableMybatisPlus
@EnableSwagger
@EnableRedisSerialize
@SpringBootApplication
//@EnableDefaultResponseAdvice
@EnableDefaultExceptionAdvice
@EnableLongToStringConfig
@EnableJwt
@EnableMq
@EnableFeignClients(basePackages = {"com.btchina.feign.clients"}, defaultConfiguration = DefaultFeignConfiguration.class)
@Slf4j
public class UserServiceApplication {

    public static void main(String[] args) {
        //获取当前配置yml文件的环境变量
        ConfigurableEnvironment env = SpringApplication.run(UserServiceApplication.class).getEnvironment();
        log.info("获取当前配置yml文件的环境变量: " + env.getProperty("spring.datasource.url"));
        log.info("nacos链接地址: " + env.getProperty("spring.cloud.nacos.server-addr"));
        //SpringApplication.run(UserServiceApplication.class, args);
    }

}
