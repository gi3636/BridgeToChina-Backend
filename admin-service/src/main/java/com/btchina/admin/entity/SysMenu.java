package com.btchina.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author franky
 * @since 2022-10-12
 */
@Getter
@Setter
@TableName("tbl_sys_menu")
@ApiModel(value = "SysMenu对象", description = "菜单表")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("上级菜单")
    private Long parentId;

    @ApiModelProperty("显示名称")
    private String title;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("别名")
    private String name;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("路由地址")
    private String path;

    @ApiModelProperty("重定向")
    private String redirect;

    @ApiModelProperty("隐藏菜单")
    private Boolean hidden;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @ApiModelProperty("创建者")
    private Long createdBy;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    @ApiModelProperty("修改人")
    private Long updatedBy;


}
