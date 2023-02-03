package com.btchina.user.model.enums;

/**
 * @author lintiancheng
 */
public enum SexEnum {


    MAN(1, "男"),
    WOMAN(2, "女")
    ;

    private Integer index;

    private String name;

    public Integer getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    SexEnum(Integer index, String name) {
        this.index = index;
        this.name = name;
    };

    public static SexEnum getByIndex(Integer index) {
        for (SexEnum sexEnum : SexEnum.values()) {
            if (sexEnum.getIndex().equals(index)) {
                return sexEnum;
            }
        }
        return null;
    }

}
