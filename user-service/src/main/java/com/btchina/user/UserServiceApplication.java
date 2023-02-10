package com.btchina.user;

import com.btchina.common.mybatis.config.EnableMybatisPlus;
import com.btchina.common.swagger.config.EnableSwagger;
import com.btchina.core.config.EnableLongToStringConfig;
import com.btchina.core.exception.EnableDefaultExceptionAdvice;
import com.btchina.redis.config.EnableRedisSerialize;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMybatisPlus
@EnableSwagger
@EnableRedisSerialize
@SpringBootApplication
//@EnableDefaultResponseAdvice
@EnableDefaultExceptionAdvice
@EnableLongToStringConfig
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
