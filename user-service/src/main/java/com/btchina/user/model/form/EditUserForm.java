package com.btchina.user.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EditUserForm {
    @ApiModelProperty("标签,多个标签用逗号隔开")
    private String tags;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("性别 0是保密 1是男 2是女")
    private Integer sex;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("简介")
    private String description;

    @ApiModelProperty("个人介绍的背景图")
    private String cover;

}
