package com.btchina.core.util.dozer;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.TypeMappingOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 对象拷贝工具类
 */
@Component
public class DozerUtil {

    @Autowired
    private Mapper dozerMapper;

    public <T> T map(Object source, Class<T> tClass){
        return dozerMapper.map(source, tClass);
    }

    public <T> List<T> copyList(List<?> sources, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (Objects.isNull(sources)) {
            return list;
        }
        for (Object o : sources) {
            T t = map(o, clazz);
            list.add(t);
        }
        return list;
    }

    public void copy(Object source, Object target) {
        if(null == source){
            return;
        }

        if(null == target){
            return;
        }

        Mapper mapper = DozerBeanMapperBuilder.create().withMappingBuilder(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(source.getClass(), target.getClass(), TypeMappingOptions.mapNull(false), TypeMappingOptions.mapEmptyString(false));
            }
        }).build();

        mapper.map(source, target);
    }

}
