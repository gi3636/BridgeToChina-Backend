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
 * 角色表
 * </p>
 *
 * @author franky
 * @since 2022-10-12
 */
@Getter
@Setter
@TableName("tbl_sys_role")
@ApiModel(value = "SysRole对象", description = "角色表")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色名称")
    private String label;

    @ApiModelProperty("角色别名")
    private String alias;

    @ApiModelProperty("排序")
    private Integer sort;

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
