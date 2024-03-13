package com.btchina.admin.entity;
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
 * 资讯表
 * </p>
 *
 * @author franky
 * @since 2023-05-07
 */
@Getter
@Setter
@TableName("tbl_news")
@Schema(name = "News对象", description = "资讯表")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description ="Id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description ="分类ID")
    private Long categoryId;

    @Schema(description ="标题")
    private String title;

    @Schema(description ="内容")
    private String content;

    @Schema(description ="是否置顶 1是置顶 0是不置顶")
    private Boolean isTop;

    @Schema(description ="来源")
    private String comeFrom;

    @Schema(description ="点赞数")
    private Integer likeCount;

    @Schema(description ="浏览数")
    private Integer viewCount;

    @Schema(description ="审核状态 0是未审核 1是审核通过 2是审核不通过")
    private Integer status;

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
