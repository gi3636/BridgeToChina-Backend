package com.btchina.user.model.enums;

import java.util.Objects;

public enum ActionEnum {

    LIKE(1, "点赞"),
    FAVORITE(2, "收藏"),
    COMMENT(3, "评论"),
    USE(4, "采用"),
    ANSWER(5, "回答"),
    ASK(6, "提问"),
    FOLLOW(7, "关注");

    public final Integer type;
    public final String content;

    ActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public static ActionEnum getMessageActionEnum(Integer type) {
        for (ActionEnum actionEnum : ActionEnum.values()) {
            if (Objects.equals(actionEnum.type, type)) {
                return actionEnum;
            }
        }
        return null;
    }
}

