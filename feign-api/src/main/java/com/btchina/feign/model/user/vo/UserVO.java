package com.btchina.feign.model.user.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    @NotNull(message = "id不能为空")
    @Schema(description ="用户id")
    private Long id;

    @Schema(description ="用户名")
    private String username;

    @Schema(description ="手机号")
    private String mobile;

    @Schema(description ="昵称")
    private String nickname;

    @Schema(description ="头像")
    private String avatar;

    @Size(max = 2, message = "性别只能是0,1或2")
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

    @Schema(description ="JwtToken")
    private String token;

}
