package com.btchina.message.netty.annotation;

import com.btchina.message.netty.ServerBoot;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Import(ServerBoot.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableNettyServer {
}

