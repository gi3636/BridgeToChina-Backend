/*
 * fuse.com.co Inc.
 * Copyright (c) 2017-2020 All Rights Reserved.
 */
package com.btchina.core.util.dozer;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类说明
 *
 * @author zhangge
 * @version OmsConfigation.java, 2020/3/27 15:43
 */
@Configuration
public class DozerConfigation {

    @Bean
    public Mapper dozerBeanMapper() {
        return DozerBeanMapperBuilder.create().withMappingBuilder(new BeanMappingBuilder() {
            @Override
            protected void configure() {
            }
        }).build();
    }

}
