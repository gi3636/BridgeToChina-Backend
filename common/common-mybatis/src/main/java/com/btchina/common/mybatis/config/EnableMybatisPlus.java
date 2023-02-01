package com.btchina.common.mybatis.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * MybatisPlus分页功能配置开关类
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MybatisPlusDefaultConfig.class)
public @interface EnableMybatisPlus {

}
