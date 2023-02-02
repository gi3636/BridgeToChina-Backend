package com.btchina.core.exception;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Web程序默认异常处理器开关
 *
 * @author 阿杆
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GlobalExceptionHandler.class)
public @interface EnableDefaultExceptionAdvice {

}
