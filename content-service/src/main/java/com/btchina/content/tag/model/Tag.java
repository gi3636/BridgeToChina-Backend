package com.btchina.content.tag.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author franky
 * @since 2023-02-06
 */
@Getter
@Setter
@TableName("tbl_tag")
@Schema(name = "Tag对象", description = "标签表")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description ="标签ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description ="标签内容")
    private String name;

    @Schema(description ="标签引用次数")
    private Integer count;

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
