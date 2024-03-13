package com.btchina.user.model.form;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EditUserForm {
    @Schema(description ="标签,多个标签用逗号隔开")
    private String tags;

    @Schema(description ="昵称")
    private String nickname;

    @Schema(description ="头像")
    private String avatar;

    @Schema(description ="性别 0是保密 1是男 2是女")
    private Integer sex;

    @Schema(description ="国家")
    private String country;

    @Schema(description ="城市")
    private String city;

    @Schema(description ="简介")
    private String description;

    @Schema(description ="个人介绍的背景图")
    private String cover;

}
