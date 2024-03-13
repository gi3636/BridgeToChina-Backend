package com.btchina.user.model.form;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名", example = "test", required = true)
    private String username;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "123456", required = true)
    private String password;

}
