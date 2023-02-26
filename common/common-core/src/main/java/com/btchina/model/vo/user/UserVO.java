package com.btchina.model.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    @NotNull(message = "id不能为空")
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("昵称;媒体号")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @Size(max = 2, message = "性别只能是0,1或2")
    @ApiModelProperty("性别 0是保密 1是男 2是女")
    private Integer sex;

    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("简介")
    private String description;

    @ApiModelProperty("个人介绍的背景图")
    private String cover;

    @ApiModelProperty("JwtToken")
    private String token;

    @ApiModelProperty("专业")
    private String major;


}
