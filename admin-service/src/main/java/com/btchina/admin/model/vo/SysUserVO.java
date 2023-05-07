package com.btchina.admin.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.btchina.admin.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "SysUser对象", description = "用户账户")
public class SysUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("盐")
    private String salt;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("修改人")
    private Long updatedBy;

    @ApiModelProperty("创建者")
    private Long createdBy;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    @ApiModelProperty("创建时间")
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
