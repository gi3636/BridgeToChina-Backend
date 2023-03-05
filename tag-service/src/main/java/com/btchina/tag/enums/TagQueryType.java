package com.btchina.tag.enums;

import java.util.Objects;

public enum TagQueryType {

    RECOMMEND(1, "推荐"),
    SEARCH(2, "搜索");


    public final Integer type;
    public final String content;

    TagQueryType(Integer type, String content) {
        this.type = type;
        this.content = content;
    }


    public static TagQueryType getType(Integer type) {
        for (TagQueryType actionEnum : TagQueryType.values()) {
            if (Objects.equals(actionEnum.type, type)) {
                return actionEnum;
            }
        }
        return null;
    }
}

