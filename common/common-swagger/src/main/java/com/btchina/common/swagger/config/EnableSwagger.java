package com.btchina.common.swagger.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Swagger功能配置开关类
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SwaggerConfig.class)
public @interface EnableSwagger {

}
