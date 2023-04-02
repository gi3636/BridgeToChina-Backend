package com.btchina.message.enums;

import java.util.Objects;

public enum DataType {

    //定义消息类型

    AckMessage(1, "回执消息"),
    ChatMessage(2, "聊天消息");

    public final Integer type;
    public final String content;

    DataType(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public static DataType getMessageActionEnum(Integer type) {
        for (DataType actionEnum : DataType.values()) {
            if (Objects.equals(actionEnum.type, type)) {
                return actionEnum;
            }
        }
        return null;
    }
}

