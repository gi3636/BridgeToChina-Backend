package com.btchina.file;

import com.btchina.common.swagger.config.EnableSwagger;
import com.btchina.core.config.EnableLongToStringConfig;
import com.btchina.core.exception.EnableDefaultExceptionAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger
@SpringBootApplication
@EnableDefaultExceptionAdvice
@EnableLongToStringConfig
public class FileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileServiceApplication.class, args);
    }

}
