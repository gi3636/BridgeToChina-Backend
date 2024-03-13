package com.btchina.admin.model.form;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SystemUserAddForm {

    @Schema(description ="用户名")
    private String username;

    @Schema(description ="密码")
    private String password;

    @Schema(description ="姓名")
    private String name;

}
