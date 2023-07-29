package com.btchina.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootTest
class UserServiceApplicationTests {

    @Test
    void contextLoads() {
        //获取当前配置yml文件的环境变量
        ConfigurableEnvironment env = SpringApplication.run(UserServiceApplication.class).getEnvironment();
        System.out.println("获取当前配置yml文件的环境变量: " + env.getProperty("spring.datasource.url"));
    }


}
