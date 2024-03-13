package com.btchina.content.school.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 学校表
 * </p>
 *
 * @author franky
 * @since 2024-01-15
 */
@Getter
@Setter
@TableName("tbl_school")
@Tag(name = "School对象", description = "学校表")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description ="id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description ="学校名称")
    private String name;

    @Schema(description ="学校简介")
    private String introduction;

    @Schema(description ="学校介绍")
    private String detail;

    @Schema(description ="学校图标")
    private String logo;

    @Schema(description ="排序")
    private Integer sort;

    @Schema(description ="创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @Schema(description ="更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    @Schema(description ="是否删除;1是删除，0是不删除")
    @TableLogic
    private Boolean deleted;


}
