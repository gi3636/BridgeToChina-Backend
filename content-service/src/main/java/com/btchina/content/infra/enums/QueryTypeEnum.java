package com.btchina.content.infra.enums;

import java.util.Objects;

public enum QueryTypeEnum {

    HOT(1, "热门问题"),
    NEW(2, "最新问题"),
    RECOMMEND(3, "推荐问题"),
    MY(4, "我的问题"),
    Follow(5, "关注问题");



    public final Integer type;
    public final String content;

    QueryTypeEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public static QueryTypeEnum getQueryTypeEnum(Integer type) {
        for (QueryTypeEnum actionEnum : QueryTypeEnum.values()) {
            if (Objects.equals(actionEnum.type, type)) {
                return actionEnum;
            }
        }
        return null;
    }
}

