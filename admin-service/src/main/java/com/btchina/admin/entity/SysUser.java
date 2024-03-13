package com.btchina.admin.entity;

import com.baomidou.mybatisplus.annotation.*;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户账户
 * </p>
 *
 * @author franky
 * @since 2022-10-12
 */
@Getter
@Setter
@TableName("tbl_sys_user")
@Schema(name = "SysUser对象", description = "用户账户")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description ="用户名")
    private String username;

    @Schema(description ="密码")
    private String password;

    @Schema(description ="盐")
    private String salt;

    @Schema(description ="姓名")
    private String name;

    @Schema(description ="创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @Schema(description ="创建者")
    private Long createdBy;

    @Schema(description ="修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    @Schema(description ="修改人")
    private Long updatedBy;


}
