package com.btchina.common.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class CommonMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date(Instant.now().getEpochSecond() * 1000);
        this.setFieldValByName("createdTime", date, metaObject);
        this.setFieldValByName("updatedTime", date, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatedTime", new Date(Instant.now().getEpochSecond() * 1000), metaObject);
    }
}


