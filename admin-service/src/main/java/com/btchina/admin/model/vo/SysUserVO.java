package com.btchina.admin.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.btchina.admin.entity.SysUser;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "SysUser对象", description = "用户账户")
public class SysUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description ="用户名")
    private String username;

    @Schema(description ="盐")
    private String salt;

    @Schema(description ="姓名")
    private String name;

    @Schema(description ="修改人")
    private Long updatedBy;

    @Schema(description ="创建者")
    private Long createdBy;

    @Schema(description ="token")
    private String token;

    @Schema(description ="修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    @Schema(description ="创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;


    public SysUserVO(SysUser loginUser) {
        this.id = loginUser.getId();
        this.username = loginUser.getUsername();
        this.salt = loginUser.getSalt();
        this.name = loginUser.getName();
        this.updatedBy = loginUser.getUpdatedBy();
        this.createdBy = loginUser.getCreatedBy();
        this.updatedTime = loginUser.getUpdatedTime();
        this.createdTime = loginUser.getCreatedTime();
    }
}
