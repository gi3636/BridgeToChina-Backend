package com.btchina.content.news.model;

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
 * 类别表
 * </p>
 *
 * @author franky
 * @since 2023-04-23
 */
@Getter
@Setter
@TableName("tbl_category")
@Tag(name = "Category对象", description = "类别表")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description ="分类ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description ="名称")
    private String name;

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
