package com.btchina.core.config;

import com.btchina.util.JwtTokenUtil;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JwtTokenUtil.class)
public @interface EnableJwt{
}
