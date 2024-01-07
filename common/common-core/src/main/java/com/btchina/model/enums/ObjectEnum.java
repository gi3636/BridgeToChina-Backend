package com.btchina.model.enums;


import java.util.Objects;

public enum ObjectEnum {

    QUESTION(1, "问题"),
    USER(2, "用户"),
    Answer(3, "回答");


    public final Integer type;
    public final String content;

    ObjectEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public static ObjectEnum geObjectEnum(Integer type) {
        for (ObjectEnum objectEnum : ObjectEnum.values()) {
            if (Objects.equals(objectEnum.type, type)) {
                return objectEnum;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }
    public String getContent() {
        return content;
    }
}

