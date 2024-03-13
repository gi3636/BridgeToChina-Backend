package com.btchina.admin.entity;

import com.baomidou.mybatisplus.annotation.*;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "SysMenu对象", description = "菜单表")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description ="上级菜单")
    private Long parentId;

    @Schema(description ="显示名称")
    private String title;

    @Schema(description ="类型")
    private String type;

    @Schema(description ="别名")
    private String name;

    @Schema(description ="菜单图标")
    private String icon;

    @Schema(description ="路由地址")
    private String path;

    @Schema(description ="重定向")
    private String redirect;

    @Schema(description ="隐藏菜单")
    private Boolean hidden;

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
