package com.btchina.common.mybatis.config;

import com.btchina.common.mybatis.handler.CommonMetaObjectHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * MybatisPlus分页功能配置开关类
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MybatisPlusDefaultConfig.class, CommonMetaObjectHandler.class})
public @interface EnableMybatisPlus {

}
