package com.btchina.user.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description:注册表单
 * @author: fenggi123
 * @create: 8/13/2021 11:58 AM
 */
@Data
public class RegisterForm {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", example = "test", required = true)
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", example = "123456", required = true)
    private String password;

    @NotBlank(message = "确认密码不能为空")
    @ApiModelProperty(value = "确认密码", example = "123456", required = true)
    private String confirmPassword;
}
