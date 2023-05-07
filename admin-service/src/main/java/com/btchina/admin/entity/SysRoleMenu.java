package com.btchina.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单表
 * </p>
 *
 * @author franky
 * @since 2022-10-12
 */
@Getter
@Setter
@TableName("tbl_sys_role_menu")
@ApiModel(value = "SysRoleMenu对象", description = "角色菜单表")
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("菜单ID")
    private Long menuId;


}
