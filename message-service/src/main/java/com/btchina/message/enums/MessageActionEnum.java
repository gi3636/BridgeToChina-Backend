package com.btchina.message.enums;

import java.util.Objects;

public enum MessageActionEnum {

    //定义消息类型

    CONNECT(1, "第一次（或重连）初始化连接"),
    CHAT(2, "聊天消息"),
    SIGNED(3, "消息签收"),
    KEEPALIVE(4, "客户端保持心跳"),
    PULL_FRIEND(5, "拉取好友");

    public final Integer type;
    public final String content;

    MessageActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public static MessageActionEnum getMessageActionEnum(Integer type) {
        for (MessageActionEnum actionEnum : MessageActionEnum.values()) {
            if (Objects.equals(actionEnum.type, type)) {
                return actionEnum;
            }
        }
        return null;
    }
}

