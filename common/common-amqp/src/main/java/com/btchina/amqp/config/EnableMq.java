package com.btchina.amqp.config;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MqConfig.class)
public @interface EnableMq {

}
