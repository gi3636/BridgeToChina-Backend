package com.btchina.model.enums;

import java.util.Objects;

public enum ObjectEnum {

    QUESTION(1, "问题"),
    USER(2, "用户");


    public final Integer type;
    public final String content;

    ObjectEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public static ObjectEnum getMessageActionEnum(Integer type) {
        for (ObjectEnum actionEnum : ObjectEnum.values()) {
            if (Objects.equals(actionEnum.type, type)) {
                return actionEnum;
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

