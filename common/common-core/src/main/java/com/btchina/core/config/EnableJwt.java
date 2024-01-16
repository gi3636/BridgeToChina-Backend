package com.btchina.core.config;

import com.btchina.core.util.JwtTokenUtil;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JwtTokenUtil.class)
public @interface EnableJwt{
}
