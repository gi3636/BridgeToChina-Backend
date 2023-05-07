package com.btchina.admin.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", example = "franky", required = true)
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", example = "123123", required = true)
    private String password;

}
